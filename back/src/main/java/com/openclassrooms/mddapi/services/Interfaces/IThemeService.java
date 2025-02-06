package com.openclassrooms.mddapi.services.Interfaces;

import java.util.List;

import com.openclassrooms.mddapi.dto.ThemeDTO;

public interface IThemeService {

    List<ThemeDTO> getAllUserThemes(String userEmail);

    List<ThemeDTO> getAllThemes(String userEmail);

}
