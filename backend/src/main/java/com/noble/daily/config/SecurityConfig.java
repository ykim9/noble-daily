package com.noble.daily.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정
 * TODO: OAuth2 인증 구현 예정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // 테스트용 CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // 현재는 모든 요청 허용 (테스트용)
                );
        
        return http.build();
    }
}
