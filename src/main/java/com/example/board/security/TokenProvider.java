package com.example.board.security;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.board.dto.TokenDto;

import io.jsonwebtoken.*;


import java.security.Key;
import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;


@Service
public class TokenProvider {
    private final String secretKey;
    private final String refreshSecretKey;
    private final long expirationHours;
    private final String issuer;
    public TokenProvider(
            @Value("${secret-key}") String secretKey,
            @Value("${expiration-hours}") long expirationHours,
            @Value("${issuer}") String issuer,
            @Value("${refresh-secret-key}") String refreshSecretKey
    ) {
        this.secretKey = secretKey;
        this.expirationHours = expirationHours;
        this.issuer = issuer;
        this.refreshSecretKey = refreshSecretKey;
    }
    
    //access token
    public TokenDto createToken(String userId) {
    	

    	String accessToken = Jwts.builder()
    			.setSubject(userId)
    			.setIssuedAt(new Date(System.currentTimeMillis()))
    			.setExpiration(new Date(System.currentTimeMillis() + this.expirationHours))
    			.signWith(SignatureAlgorithm.HS512, this.secretKey)
    			.compact();
    	
    	
    	String refreshToken = Jwts.builder()
    			.setSubject(userId)
    			.setIssuedAt(new Date(System.currentTimeMillis()))
    			.setExpiration(new Date(System.currentTimeMillis() + this.expirationHours))
    			.signWith(SignatureAlgorithm.HS512, this.refreshSecretKey)
    			.compact();
    
    	return TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).key(userId).build();
    
    }
    
    public String getUserIdFromToken(String token) {
    	String userId = Jwts.parser()
    			.setSigningKey(secretKey)
    			.parseClaimsJws(token)
    			.getBody()
    			.getSubject();
    	System.out.println("token : " + token);
        System.out.println("userId in function: " + userId);
    	return userId;
    } 
    
}