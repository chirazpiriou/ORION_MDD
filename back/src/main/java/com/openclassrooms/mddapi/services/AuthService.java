package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.configuration.JwtUtil;
import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.UserRepository;

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

    public String login(UserModel user) {
        // Authentifie l'utilisateur
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        // Génère et retourne un JWT après l'authentification réussie
        return jwtUtil.generateToken(user.getEmail());
    }

    public String updateUserDetails(Integer userId, String newName, String newEmail) {

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setName(newName);
        user.setEmail(newEmail);
        userRepository.save(user);
        return jwtUtil.generateToken(user.getEmail());
    }
}