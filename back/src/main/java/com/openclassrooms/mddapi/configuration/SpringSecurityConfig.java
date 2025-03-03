package com.openclassrooms.mddapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * SpringSecurityConfig class configures the security settings for the
 * application.
 * It defines authentication and authorization settings, session management,
 * CORS configuration, and sets up JWT authentication filters.
 */
@Configuration // Marks this class as a configuration class for Spring to manage beans.
public class SpringSecurityConfig {

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter; // Injects the JWT authentication filter to be used in
                                                                 // the security filter chain.

        @Autowired
        private CorsConfig corsConfig; // Injects the CORS configuration class to handle cross-origin requests.

        /**
         * Configures the HTTP security settings.
         * 
         * @param http The HttpSecurity object to configure security settings for HTTP
         *             requests.
         * @return The configured SecurityFilterChain.
         * @throws Exception In case of configuration issues.
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                // Disables CSRF protection, which is useful for stateless APIs (like those
                // using JWT).
                http.csrf(csrf -> csrf.disable())

                                // Configures CORS with the provided CorsConfig class.
                                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))

                                // Configures session management to be stateless (does not store session
                                // information on the server).
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                // Configures the authorization of requests:
                                // - Permit access to login and register API endpoints.
                                // - Require authentication for the update API endpoint.
                                // - All other requests require authentication.
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(new AntPathRequestMatcher("/api/auth/login", "POST"))
                                                .permitAll()
                                                .requestMatchers(
                                                                new AntPathRequestMatcher("/api/auth/register", "POST"))
                                                .permitAll()
                                                .requestMatchers(new AntPathRequestMatcher("/api/auth/update", "PUT"))
                                                .authenticated()
                                                .anyRequest().authenticated())

                                // Adds the JWT authentication filter before the default username/password
                                // authentication filter.
                                .addFilterBefore(jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build(); // Returns the configured SecurityFilterChain.
        }

        /**
         * Configures the password encoder to use BCrypt for encoding passwords.
         * 
         * @return A BCryptPasswordEncoder instance.
         */
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(); // Using BCrypt hashing algorithm to encode passwords securely.
        }

        /**
         * Configures the AuthenticationManager to manage authentication for the
         * application.
         * 
         * @param authenticationConfiguration The AuthenticationConfiguration object to
         *                                    obtain the manager.
         * @return The configured AuthenticationManager.
         * @throws Exception In case of configuration issues.
         */
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager(); // Returns the AuthenticationManager
                                                                               // bean.
        }
}
