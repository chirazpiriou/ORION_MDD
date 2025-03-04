package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.models.AbonnementModel;
import com.openclassrooms.mddapi.models.ThemeModel;
import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.AbonnementRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.Interfaces.IThemeService;

/**
 * Service class responsible for handling theme-related operations.
 */
@Service
public class ThemeService implements IThemeService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private ThemeRepository themeRepository;
    
    @Autowired
    private AbonnementRepository abonnementRepository;

    /**
     * Retrieves all themes that the authenticated user is subscribed to.
     *
     * @param userEmail The email of the authenticated user.
     * @return A list of subscribed themes.
     */
    @Override
    public List<ThemeDTO> getAllUserThemes(String userEmail) {
        // Retrieve the user by email, throw an exception if not found.
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        // Get the IDs of themes the user is subscribed to.
        List<Integer> subscribedThemeIds = abonnementRepository.findAllByUserId(user.getId())
                .stream()
                .map(AbonnementModel::getThemeId)
                .collect(Collectors.toList());

        // Fetch subscribed themes by their IDs.
        List<ThemeModel> subscribedThemes = themeRepository.findAllByIdIn(subscribedThemeIds);

        // Convert to DTOs and mark them as subscribed.
        return subscribedThemes.stream()
                .map(theme -> {
                    ThemeDTO themeDTO = modelMapper.map(theme, ThemeDTO.class);
                    themeDTO.setSubscribed(true);
                    return themeDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all available themes while indicating whether the authenticated
     * user is subscribed to them.
     *
     * @param userEmail The email of the authenticated user.
     * @return A list of themes with subscription status.
     */
    @Override
    public List<ThemeDTO> getAllThemes(String userEmail) {
        // Retrieve the user by email, throw an exception if not found.
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        // Get the IDs of themes the user is subscribed to.
        List<Integer> subscribedThemeIds = abonnementRepository.findAllByUserId(user.getId())
                .stream()
                .map(AbonnementModel::getThemeId)
                .collect(Collectors.toList());

        // Fetch all themes from the repository.
        List<ThemeModel> allThemes = themeRepository.findAll();

        // Convert to DTOs while setting subscription status.
        return allThemes.stream()
                .map(theme -> {
                    ThemeDTO themeResponseDTO = modelMapper.map(theme, ThemeDTO.class);
                    themeResponseDTO.setSubscribed(subscribedThemeIds.contains(theme.getId()));
                    return themeResponseDTO;
                })
                .collect(Collectors.toList());
    }
}
