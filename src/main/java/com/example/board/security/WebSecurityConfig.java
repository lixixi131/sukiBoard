package com.example.board.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration	
public class WebSecurityConfig	  {
	
	
    private final JwtAuthenticationFilter jwtAuthenticationFilter;	// JwtAuthenticationFilter 주입
	
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(c -> c.disable())
                //.cors(c -> c.disable())
                .cors( c -> c.configurationSource(corsConfig.corsConfigurationSource()))
                .headers((h) -> h.frameOptions(f -> f.disable()))
                .formLogin( c -> c.disable())
                .authorizeHttpRequests( (auth) -> {
                	auth
                		
                		.requestMatchers("/post").permitAll()
                		.requestMatchers("/comment/list").permitAll()
                		.requestMatchers("/post/search").permitAll()
                		.requestMatchers("/post/list").permitAll()
                		.requestMatchers("/logIn/**").permitAll()
                		.requestMatchers("/signUp/**").permitAll()
                		.anyRequest().authenticated();
                })
                //.addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class).build();	// 추가
                .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class)
                .build();

                
                			
        //return http.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class).build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration config = new CorsConfiguration();
    	
    	config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://ec2-54-180-91-222.ap-northeast-2.compute.amazonaws.com:8080"); // 프론트 IPv4 주소

    	config.addAllowedMethod("*");
    	config.addAllowedHeader("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
    	
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

    	return source;
    	
    	
    }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
