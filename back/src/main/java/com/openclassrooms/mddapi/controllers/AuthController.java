package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.services.AuthService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController handles authentication-related endpoints such as user
 * registration, login,
 * profile retrieval, and updating user information.
 */
@RestController // Marks the class as a Spring REST controller to handle HTTP requests and
                // return JSON responses.
@RequestMapping("/api/auth") // Specifies the base URL for all authentication-related API endpoints.
public class AuthController {

    @Autowired
    private AuthService authService; // Autowires the AuthService to handle authentication logic.

    /**
     * Endpoint to register a new user.
     * 
     * @param user The user information to be registered.
     * @return A response entity containing the token if registration is successful.
     */
    @PostMapping("/register") // Handles POST requests to register a new user.
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        String token = authService.register(user); // Calls the AuthService to register the user and get a token.
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("token", token)); // Returns a response with the registration token.
    }

    /**
     * Endpoint for user login.
     * 
     * @param loginRequest A map containing the user's identifier (email or
     *                     username) and password.
     * @return A response entity with the login token if successful, or an error
     *         message if missing data.
     */
    @PostMapping("/login") // Handles POST requests to log in an existing user.
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String identifier = loginRequest.get("identifier"); // Can be email or username.
        String password = loginRequest.get("password");

        // If either identifier or password is missing, return a bad request error.
        if (identifier == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing data"));
        }

        // Call the AuthService to log the user in and retrieve the login token.
        String token = authService.login(identifier, password);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("token", token)); // Returns the token in the response.
    }

    /**
     * Endpoint to update user information.
     * 
     * @param updatedUser    The updated user data.
     * @param authentication The authentication object that contains the currently
     *                       authenticated user's details.
     * @return A response entity indicating success or failure.
     */
    @PutMapping("/update") // Handles PUT requests to update user information.
    public ResponseEntity<?> updateUser(@RequestBody UserModel updatedUser, Authentication authentication) {
        // If the user is not authenticated, return an unauthorized error.
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
        }

        String currentUserEmail = authentication.getName(); // Get the currently authenticated user's email.
        Map<String, String> response = authService.updateUser(currentUserEmail, updatedUser); // Call the service to
                                                                                              // update user info.
        return ResponseEntity.ok(response); // Return the response after updating user information.
    }

    /**
     * Endpoint to retrieve the authenticated user's profile.
     * 
     * @param authentication The authentication object to get the currently
     *                       authenticated user's details.
     * @return A response entity with the user's profile data or an error message if
     *         not found.
     */
    @GetMapping("/profile") // Handles GET requests to retrieve the user's profile.
    @PreAuthorize("isAuthenticated()") // Ensures that the user must be authenticated to access this endpoint.
    public ResponseEntity<?> getProfile(Authentication authentication) {
        // If the user is not authenticated, return an unauthorized error.
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
        }

        String email = authentication.getName(); // Retrieve the email of the authenticated user.
        UserModel user = authService.getUserByEmail(email); // Get the user's profile from the service.

        // If the user is not found, return a 404 Not Found response.
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        return ResponseEntity.ok(user); // Return the user's profile data as a 200 OK response.
    }
}
