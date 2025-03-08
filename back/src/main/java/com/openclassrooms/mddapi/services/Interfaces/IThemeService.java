package com.openclassrooms.mddapi.services.Interfaces;

import java.util.List;
import com.openclassrooms.mddapi.dto.ThemeDTO;

/**
 * Interface for the Theme Service.
 * Defines methods related to retrieving themes for users.
 */
public interface IThemeService {

    List<ThemeDTO> getAllUserThemes(String userEmail);

    List<ThemeDTO> getAllThemes(String userEmail);
}
