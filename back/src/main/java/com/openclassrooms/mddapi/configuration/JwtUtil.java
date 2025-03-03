package com.openclassrooms.mddapi.configuration;

import java.util.Date;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

/**
 * JwtUtil class is responsible for generating, parsing, and validating JWT
 * tokens.
 * It uses the io.jsonwebtoken library to handle JSON Web Tokens (JWT).
 */
@Component
public class JwtUtil {

    // The secret key used for signing JWTs. It's generated using the HMAC-SHA256
    // algorithm.
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Method to generate a JWT token for a given username.
     * The generated token contains the username as the subject, an issue date,
     * and an expiration date set to 10 hours from the current time.
     * 
     * @param username The username to be included in the token's subject.
     * @return The generated JWT token as a String.
     */
    public String generateToken(String username) {
        return Jwts.builder() // Start building the JWT
                .setSubject(username) // Set the subject (user) for the token
                .setIssuedAt(new Date()) // Set the issue date to the current date and time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Set the expiration time
                                                                                           // (10 hours)
                .signWith(SECRET_KEY) // Sign the JWT with the secret key
                .compact(); // Generate the JWT as a string
    }

    /**
     * Method to extract the username (subject) from a JWT token.
     * 
     * @param token The JWT token from which the username is to be extracted.
     * @return The username (subject) from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Extract the subject (username) using the extractClaim method
    }

    /**
     * Method to extract the expiration date from a JWT token.
     * 
     * @param token The JWT token from which the expiration date is to be extracted.
     * @return The expiration date of the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Extract the expiration date using the extractClaim method
    }

    /**
     * Generic method to extract any claim from a JWT token based on the provided
     * claimsResolver.
     * 
     * @param token          The JWT token from which a claim is to be extracted.
     * @param claimsResolver A function that extracts the desired claim from the
     *                       Claims object.
     * @param <T>            The type of the claim to be extracted.
     * @return The extracted claim of type T.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = Jwts.parserBuilder() // Build a JWT parser
                    .setSigningKey(SECRET_KEY) // Set the signing key for validation
                    .build()
                    .parseClaimsJws(token) // Parse the JWT token
                    .getBody(); // Get the body of the JWT containing the claims
            return claimsResolver.apply(claims); // Apply the provided claimsResolver to extract the desired claim
        } catch (JwtException | IllegalArgumentException e) {
            // Log the exception or handle it as needed
            throw new RuntimeException("JWT Token is invalid or expired", e); // If parsing fails, throw an exception
        }
    }

    /**
     * Method to check if a token is valid by ensuring the username matches and the
     * token is not expired.
     * 
     * @param token    The JWT token to be validated.
     * @param username The username to be checked against the token's subject.
     * @return True if the token is valid, otherwise false.
     */
    public boolean isTokenValid(String token, String username) {
        return (extractUsername(token).equals(username) && !isTokenExpired(token)); // Check validity based on username
                                                                                    // and expiration
    }

    /**
     * Helper method to check if a token has expired by comparing the expiration
     * date with the current date.
     * 
     * @param token The JWT token to be checked for expiration.
     * @return True if the token has expired, otherwise false.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Check if the token's expiration date is before the
                                                            // current date
    }
}
