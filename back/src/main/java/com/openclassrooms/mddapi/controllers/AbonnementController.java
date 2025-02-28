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

@RestController
@RequestMapping("/api/abonnement") // The base URL for abonnement-related operations.
public class AbonnementController {
    @Autowired
    private IAbonnementService abonnementService; // Injecting the AbonnementService to handle the logic.

    /**
     * Endpoint to change the subscription status of a user to a specific theme.
     *
     * @param themeId   The ID of the theme.
     * @param userEmail The email of the user.
     * @return A response containing the result of the subscription change.
     */
    @GetMapping("/subscription/{id}")
    public ResponseEntity<String> changeSubscriptionStatus(@PathVariable("id") Integer themeId,
            Authentication authentication) {
               
        String userEmail = authentication.getName();
        try {
            // Call the service method to change the subscription status.
            String responseMessage = abonnementService.changeSubscriptionStatus(themeId, userEmail);
            return ResponseEntity.ok(responseMessage); // Return the success response (OK - 200).
        } catch (UsernameNotFoundException e) {
            // Handle the case where the user was not found (NOT_FOUND - 404)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error:L'utilisateur n'a pas été trouvé  " + userEmail);
        } catch (IllegalArgumentException e) {
            // Handle invalid theme ID (NOT_FOUND - 404)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Thème non trouvé  " + themeId);
        } catch (Exception e) {
            // Catch other unforeseen errors (INTERNAL_SERVER_ERROR - 500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}
