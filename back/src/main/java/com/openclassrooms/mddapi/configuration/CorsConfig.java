package com.openclassrooms.mddapi.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
// The annotation indicating that this class is a Spring configuration
public class CorsConfig {

    @Bean
    // Declaration of a bean for CORS configuration in the Spring context
    public CorsConfigurationSource corsConfigurationSource() {

        // Creating a CorsConfiguration object to define CORS rules
        CorsConfiguration configuration = new CorsConfiguration();

        // Setting the allowed origins for CORS requests
        // Here, only the origin "http://localhost:4200" is allowed (typically for a
        // development Angular app)
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        // Setting the allowed HTTP methods for CORS requests
        // This includes GET, POST, PUT, DELETE, and OPTIONS methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Setting the allowed headers for CORS requests
        // Only the "Authorization" and "Content-Type" headers are allowed here
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Allowing credentials (cookies, authorization headers, etc.)
        configuration.setAllowCredentials(true);

        // Creating a UrlBasedCorsConfigurationSource object to register the CORS
        // configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Registering the CORS configuration for all URLs of the application ("/**")
        source.registerCorsConfiguration("/**", configuration);

        // Returning the CORS configuration source
        return source;
    }
}
