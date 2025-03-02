package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.configuration.JwtUtil;
import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.UserRepository;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(UserModel user) {
        // Encode le mot de passe avant de sauvegarder
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return jwtUtil.generateToken(user.getEmail());
    }

    public String login(String identifier, String password) {
        UserModel user = userRepository.findByEmail(identifier)
        .orElseGet(() -> userRepository.findByName(identifier)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé")));

        // Authentifie l'utilisateur
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(identifier, password));

        // Génère et retourne un JWT après l'authentification réussie
        return jwtUtil.generateToken(user.getEmail());
    }

    public Map<String, String> updateUser(String email, UserModel updatedUser) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }

        userRepository.save(user);
        String newToken = jwtUtil.generateToken(user.getEmail());

        return Map.of("message", "Utilisateur mis à jour avec succès", "token", newToken);
    }

    public UserModel getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
