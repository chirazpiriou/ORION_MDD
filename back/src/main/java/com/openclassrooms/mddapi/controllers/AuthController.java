package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.TokenResponse;
import com.openclassrooms.mddapi.dto.UserDTO;

import com.openclassrooms.mddapi.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserDTO userDTO) {
        try {

            TokenResponse tokenResponse = authService.login(userDTO);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse("Invalid credentials"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody UserDTO userDTO) {
        try {
            TokenResponse token = authService.register(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TokenResponse(e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TokenResponse> updateUser(Authentication authentication, @RequestBody UserDTO userDTO) {
        try {
            String userEmail = authentication.getName();
            TokenResponse tokenResponse = authService.updateUser(userEmail, userDTO);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TokenResponse("User not found"));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(Authentication authentication) {
        String userEmail = authentication.getName();
        UserDTO userDTO = authService.getUserProfile(userEmail);
        return ResponseEntity.ok(userDTO);
    }
}
