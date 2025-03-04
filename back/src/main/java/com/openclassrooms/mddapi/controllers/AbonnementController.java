package com.openclassrooms.mddapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.services.Interfaces.IAbonnementService;

/**
 * AbonnementController handles subscription-related API operations such as
 * changing
 * the subscription status for a user to a specific theme.
 */
@RestController // This annotation defines the class as a REST controller in Spring, returning
                // JSON responses.
@RequestMapping("/api/abonnement") // Specifies the base URL for abonnement-related API endpoints (e.g.,
                                   // /api/abonnement/subscription/{id}).
public class AbonnementController {

    @Autowired
    private IAbonnementService abonnementService; // Injects the service to handle subscription logic.

    /**
     * Endpoint to change the subscription status of a user to a specific theme.
     * 
     * @param themeId        The ID of the theme to subscribe/unsubscribe to.
     * @param authentication The authentication object to get the currently
     *                       authenticated user's email.
     * @return A response containing a success or error message.
     */
    @GetMapping("/subscription/{id}") // Maps GET requests to change the subscription status for a given theme ID.
    public ResponseEntity<String> changeSubscriptionStatus(@PathVariable("id") Integer themeId,
            Authentication authentication) {

        // Retrieves the currently authenticated user's email.
        String userEmail = authentication.getName();

        try {
            // Calls the service method to change the subscription status based on themeId
            // and userEmail.
            String responseMessage = abonnementService.changeSubscriptionStatus(themeId, userEmail);
            return ResponseEntity.ok(responseMessage); // Returns a success message with HTTP status OK (200).
        } catch (UsernameNotFoundException e) {
            // Handles the case where the user is not found, returning HTTP status NOT_FOUND
            // (404).
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: User not found " + userEmail);
        } catch (IllegalArgumentException e) {
            // Handles invalid theme ID by returning HTTP status NOT_FOUND (404).
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Theme not found " + themeId);
        } catch (Exception e) {
            // Handles other unforeseen errors with HTTP status INTERNAL_SERVER_ERROR (500).
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
