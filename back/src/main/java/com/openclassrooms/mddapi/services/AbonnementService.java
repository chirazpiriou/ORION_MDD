package com.openclassrooms.mddapi.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.models.AbonnementModel;
import com.openclassrooms.mddapi.models.ThemeModel;
import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.AbonnementRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.Interfaces.IAbonnementService;

@Service // Marks this class as a Spring service component
public class AbonnementService implements IAbonnementService {

    @Autowired
    ThemeRepository themeRepository; // Injects the ThemeRepository dependency

    @Autowired
    private UserRepository userRepository; // Injects the UserRepository dependency

    @Autowired
    AbonnementRepository abonnementRepository; // Injects the AbonnementRepository dependency

    @Override
    public String changeSubscriptionStatus(Integer themeId, String userEmail) {
        // Retrieve the user by email, throwing an exception if not found
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        // Retrieve the theme by ID, throwing an exception if not found
        ThemeModel theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new IllegalArgumentException("Theme not found with ID: " + themeId));

        // Check if the user is already subscribed to the theme
        Optional<AbonnementModel> abonnementModel = abonnementRepository.findByUserIdAndThemeId(user.getId(), themeId);
        String responseMessage;

        if (abonnementModel.isPresent()) {
            // If the subscription exists, unsubscribe the user
            abonnementRepository.delete(abonnementModel.get());
            responseMessage = "Se désabonner de " + theme.getTheme(); // Unsubscribe message (in French)
        } else {
            // If not subscribed, create a new subscription
            abonnementRepository.save(new AbonnementModel(themeId, user.getId()));
            responseMessage = "S'abonner à " + theme.getTheme(); // Subscribe message (in French)
        }

        return responseMessage; // Return the subscription status message
    }
}
