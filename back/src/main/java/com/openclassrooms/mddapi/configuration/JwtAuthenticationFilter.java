package com.openclassrooms.mddapi.configuration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;

// JWT authentication filter that extends OncePerRequestFilter
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Dependency injection
    @Autowired
    private JwtUtil jwtUtil; // Utility for handling JWT
    @Autowired
    private UserDetailsService userDetailsService; // Service for loading user details

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, java.io.IOException {

        // Check if JwtUtil is properly initialized before proceeding
        if (jwtUtil == null) {
            throw new IllegalStateException("JwtUtil is not initialized.");
        }

        // Check if the request is for login or registration
        // In this case, we do not filter the requests and pass them directly
        if (request.getRequestURI().equals("/api/auth/login") || request.getRequestURI().equals("/api/auth/register")) {
            chain.doFilter(request, response);
            return;
        }

        // Extract the "Authorization" header from the request
        String authHeader = request.getHeader("Authorization");

        // If the header is absent or does not start with "Bearer ", pass the request
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Retrieve the JWT token from the header (after "Bearer ")
        String token = authHeader.substring(7);

        // Extract the username from the token
        String username = jwtUtil.extractUsername(token);

        // If a username is extracted and the user is not yet authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the service
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate the token
            if (jwtUtil.isTokenValid(token, userDetails.getUsername())) {
                // Create the authentication object with the user's authorities
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // The authentication is set in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Pass the request to the next filter
        chain.doFilter(request, response);
    }
}
