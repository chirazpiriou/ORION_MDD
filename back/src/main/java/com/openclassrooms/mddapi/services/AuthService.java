package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.configuration.JwtUtil;
import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.UserRepository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for authentication and user management operations.
 */
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user by encoding their password and generating a JWT token.
     *
     * @param user The user to be registered.
     * @return The generated JWT token.
     */
    public String register(UserModel user) {
        // Encode the user's password before saving to the database.
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        
        // Generate and return a JWT token for the newly registered user.
        return jwtUtil.generateToken(user.getEmail());
    }

    /**
     * Authenticates a user using their email or username and password.
     *
     * @param identifier The email or username of the user.
     * @param password   The user's password.
     * @return A JWT token if authentication is successful.
     */
    public String login(String identifier, String password) {
        // Retrieve user by email or username.
        UserModel user = userRepository.findByEmail(identifier)
                .orElseGet(() -> userRepository.findByName(identifier)
                        .orElseThrow(() -> new RuntimeException("User not found")));

        // Authenticate the user using Spring Security's authentication manager.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(identifier, password));

        // Generate and return a JWT token after successful authentication.
        return jwtUtil.generateToken(user.getEmail());
    }

    /**
     * Updates a user's details (name and/or email) and returns a new JWT token.
     *
     * @param email        The email of the user to be updated.
     * @param updatedUser  The updated user details.
     * @return A map containing a success message and a new JWT token.
     */
    public Map<String, String> updateUser(String email, UserModel updatedUser) {
        // Retrieve the user by email, or throw an exception if not found.
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update user details if provided.
        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }

        // Save the updated user data.
        userRepository.save(user);

        // Generate a new JWT token with the updated email.
        String newToken = jwtUtil.generateToken(user.getEmail());

        // Return a response with a success message and the new token.
        return Map.of("message", "User successfully updated", "token", newToken);
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return The corresponding UserModel object or null if not found.
     */
    public UserModel getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
