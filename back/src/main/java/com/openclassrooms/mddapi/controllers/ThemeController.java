package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.services.ThemeService;

@RestController
@RequestMapping("/api/theme")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @GetMapping("/all")
    public ResponseEntity<List<ThemeDTO>> getAllThemes(Authentication authentication) {
        String userEmail = authentication.getName();
        List<ThemeDTO> themes = themeService.getAllThemes(userEmail);
        return ResponseEntity.ok(themes);
    }

    @GetMapping("/user")
    public ResponseEntity<List<ThemeDTO>> getAllUserThemes(Authentication authentication) {
        String userEmail = authentication.getName();
        List<ThemeDTO> userThemes = themeService.getAllUserThemes(userEmail);
        return ResponseEntity.ok(userThemes);
    }
}
