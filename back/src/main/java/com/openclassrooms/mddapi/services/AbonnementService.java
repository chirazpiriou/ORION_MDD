package com.openclassrooms.mddapi.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
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

@Service
public class AbonnementService implements IAbonnementService {

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AbonnementRepository abonnementRepository;

    @Override
    public String changeSubscriptionStatus(Integer themeId, String userEmail) {
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        ThemeModel theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new IllegalArgumentException("Theme not found with ID: " + themeId));

        Optional<AbonnementModel> abonnementModel = abonnementRepository.findByUserIdAndThemeId(user.getId(), themeId);
        String responseMessage;

        if (abonnementModel.isPresent()) {
            abonnementRepository.delete(abonnementModel.get());
            responseMessage = "Se désabonner de " + theme.getTheme();
        } else {
            abonnementRepository.save(new AbonnementModel(themeId, user.getId()));
            responseMessage = "S'abonner à " + theme.getTheme();
        }

        return responseMessage;
    }

}
