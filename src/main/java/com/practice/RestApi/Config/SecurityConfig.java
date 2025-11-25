package com.practice.RestApi.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF for POST requests (dev only)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/signup", "/addStudent", "/student").permitAll()  // Allow these without auth
                .anyRequest().authenticated()  // Other requests require auth
            );
        return http.build();
    }
}
