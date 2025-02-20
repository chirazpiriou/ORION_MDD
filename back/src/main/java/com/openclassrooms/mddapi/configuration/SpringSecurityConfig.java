package com.openclassrooms.mddapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(new CorsConfig().corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/api/article/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/theme/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/abonnement/subscription/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/commentaire/**")).permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
