package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Retrieves all themes and indicates if the user is subscribed to each.
     * 
     * @param userEmail The email of the user.
     * @return A list of {@link ThemeDTO} with subscription status.
     * @throws RuntimeException if the user is not found.
     */

    @Override
    public List<ThemeDTO> getAllThemes(String userEmail) {
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<ThemeModel> themes = themeRepository.findAll();
        return themes.stream()
                .map(theme -> {
                    ThemeDTO dto = modelMapper.map(theme, ThemeDTO.class);
                    boolean isSubscribed = theme.getAbonnements().stream()
                            .anyMatch(abonnement -> abonnement.getUser().getId().equals(user.getId()));
                    dto.setSubscribed(isSubscribed);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all themes the user is subscribed to.
     * 
     * @param email The email of the user.
     * @return A list of {@link ThemeDTO} representing the user's subscriptions.
     * @throws RuntimeException if the user is not found.
     */

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
