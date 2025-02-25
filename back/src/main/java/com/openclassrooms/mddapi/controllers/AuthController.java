package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.services.AuthService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        String token = authService.login(user);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("token", token));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserModel user) {
        try {
            // Appel à la méthode de mise à jour du service
            String updatedToken = authService.updateUserDetails(user.getId(), user.getName(), user.getEmail());

            // Retourne le token JWT mis à jour
            return ResponseEntity.ok(updatedToken);
        } catch (RuntimeException e) {
            // Si une erreur se produit (par exemple, utilisateur non trouvé), retourner un
            // code 400 avec un message d'erreur
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        }
    }
}