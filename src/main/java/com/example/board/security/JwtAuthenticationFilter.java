package com.example.board.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.example.board.dto.*;

@Order(0)
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final TokenProvider tokenProvider;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Token에서 Claim 꺼내기
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("헤더 생긴거 확인 : " +authorizationHeader);
        if(authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ") ||
                authorizationHeader.equals("Bearer null") )
        {      //header에 AUTHORIZATION이 없거나, Bearer로 시작하지 않으면 filter
            System.out.println("header가 없거나 형식이 틀립니다");
            filterChain.doFilter(request, response);
            return;
        }


        String token;
        try {
            token = authorizationHeader.split(" ")[1].trim();
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }
        //log.info("token : {}", token);

        //토큰이 Valid한지 확인하기
//        if(tokenProvider.isTokenExpired(token)){
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        if(!tokenProvider.validateToken(token)){
//            filterChain.doFilter(request, response);
//            return;
//        }

        //userName 넣기, 문 열어주기
        String userId = tokenProvider.getUserIdFromToken(token);

        System.out.println("userId : " + userId);
        
        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority(userId)));
        //디테일 설정하기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
	private UserDto parseUserSpecification(String token) {
        String[] split = Optional.ofNullable(token)
                .filter(subject -> subject.length() >= 10)
                .map(tokenProvider::getUserIdFromToken)
                .orElse("anonymous:anonymous")
                .split(":");
 
        //return new UserDto(split[0], "", List.of(new SimpleGrantedAuthority(split[1])));
        //return new UserDto.builder().id(split[0]).build();
        UserDto user =  new UserDto().builder().id(split[0]).build();
        return user;
	}
	private String parseBearerToken(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);	
	}
	
	
}
