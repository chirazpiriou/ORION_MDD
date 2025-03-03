package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.services.Interfaces.IThemeService;

/**
 * The ThemeController class handles API requests related to themes.
 * It provides endpoints for retrieving all themes and the themes subscribed to
 * by a specific user.
 */
@RestController // Marks the class as a REST controller to handle HTTP requests and return JSON
                // responses.
@RequestMapping("/api/theme") // Specifies the base URL for theme-related API endpoints (e.g., /api/theme).
public class ThemeController {

    @Autowired
    private IThemeService themeService; // Autowires the service to handle theme-related logic.

    /**
     * Endpoint to get all available themes for the user.
     * The user's email is fetched from the authentication object.
     *
     * @param authentication The current user's authentication object, which
     *                       contains the user's email.
     * @return A list of all themes available to the user.
     */
    @GetMapping("/all")
    public ResponseEntity<List<ThemeDTO>> getAllThemes(Authentication authentication) {
        String userEmail = authentication.getName(); // Retrieves the user's email from the authentication object.
        List<ThemeDTO> themes = themeService.getAllThemes(userEmail); // Fetches the list of themes from the service.
        return ResponseEntity.ok(themes); // Returns the list of themes as a 200 OK response.
    }

    /**
     * Endpoint to get all themes subscribed to by the user.
     * The user's email is fetched from the authentication object.
     *
     * @param authentication The current user's authentication object, which
     *                       contains the user's email.
     * @return A list of themes that the user is subscribed to.
     */
    @GetMapping("/user")
    public ResponseEntity<List<ThemeDTO>> getAllUserThemes(Authentication authentication) {
        String userEmail = authentication.getName(); // Retrieves the user's email from the authentication object.
        List<ThemeDTO> themes = themeService.getAllUserThemes(userEmail); // Fetches the list of user's subscribed
                                                                          // themes from the service.
        return ResponseEntity.ok(themes); // Returns the list of themes as a 200 OK response.
    }
}
