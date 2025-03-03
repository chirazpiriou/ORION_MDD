package com.openclassrooms.mddapi.services.Interfaces;

import java.util.List;
import com.openclassrooms.mddapi.dto.ThemeDTO;

/**
 * Interface for the Theme Service.
 * Defines methods related to retrieving themes for users.
 */
public interface IThemeService {

    /**
     * Retrieves all themes that a specific user is subscribed to.
     *
     * @param userEmail The email of the user.
     * @return A list of ThemeDTO objects representing the user's subscribed themes.
     */
    List<ThemeDTO> getAllUserThemes(String userEmail);

    /**
     * Retrieves all available themes in the system.
     * 
     * @param userEmail The email of the user (potentially for context-specific
     *                  filtering).
     * @return A list of ThemeDTO objects representing all available themes.
     */
    List<ThemeDTO> getAllThemes(String userEmail);
}
