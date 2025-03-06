package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.ThemeDTO;

import com.openclassrooms.mddapi.models.ThemeModel;
import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.Interfaces.IThemeService;

/**
 * Service class responsible for handling theme-related operations.
 */
@Service
public class ThemeService implements IThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ThemeDTO> getAllThemes(String userEmail) {
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ThemeModel> themes = themeRepository.findAll();

        return themes.stream()
                .map(theme -> {
                    ThemeDTO dto = modelMapper.map(theme, ThemeDTO.class);

                    // Vérifie si l'utilisateur a un abonnement dans la liste des abonnements du
                    // thème
                    boolean isSubscribed = theme.getAbonnements().stream()
                            .anyMatch(abonnement -> abonnement.getUser().getId().equals(user.getId()));

                    dto.setSubscribed(isSubscribed);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ThemeDTO> getAllUserThemes(String email) {
        return userRepository.findByEmail(email)
                .map(user -> user.getAbonnements())
                .orElseThrow(() -> new RuntimeException("User not found"))
                .stream()
                .map(abonnement -> abonnement.getTheme())
                .map(theme -> modelMapper.map(theme, ThemeDTO.class))
                .collect(Collectors.toList());
    }
}
