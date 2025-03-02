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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        String token = authService.register(user);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String identifier = loginRequest.get("identifier"); // Peut être un email ou un username
        String password = loginRequest.get("password");

        if (identifier == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Données manquantes"));
        }
        String token = authService.login(identifier, password);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("token", token));
    }

    @PutMapping("/update")

    public ResponseEntity<?> updateUser(@RequestBody UserModel updatedUser, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Utilisateur non authentifié"));
        }
        String currentUserEmail = authentication.getName();
        Map<String, String> response = authService.updateUser(currentUserEmail, updatedUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Utilisateur non authentifié"));
        }

        String email = authentication.getName();
        UserModel user = authService.getUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Utilisateur non trouvé"));
        }

        return ResponseEntity.ok(user);
    }
}