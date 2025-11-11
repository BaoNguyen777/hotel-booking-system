package com.example.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF để test Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/actuator/**").permitAll() // Cho phép register không cần login
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {}); // Nếu cần login thì dùng basic auth

        return http.build();
    }
}
