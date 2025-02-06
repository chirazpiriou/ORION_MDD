package com.openclassrooms.mddapi.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.services.Interfaces.IThemeService;

@RestController
@RequestMapping("/api/theme")
public class ThemeController {

    @Autowired
    private IThemeService themeService;

    @GetMapping("/all")
    public ResponseEntity<List<ThemeDTO>> getAllThemes(@RequestParam("userEmail") String userEmail) {
        List<ThemeDTO> themes = themeService.getAllThemes(userEmail);
        return ResponseEntity.ok(themes);
    }

    @GetMapping("/user")
    public ResponseEntity<List<ThemeDTO>> getAllUserThemes(@RequestParam("userEmail") String userEmail) {
        List<ThemeDTO> themes = themeService.getAllUserThemes(userEmail);
        return ResponseEntity.ok(themes);
    }
}
