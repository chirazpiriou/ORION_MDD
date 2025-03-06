package com.openclassrooms.mddapi.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.SubscriptionStatusDTO;
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
    private AbonnementRepository abonnementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Transactional
    @Override
    public SubscriptionStatusDTO changeSubscriptionStatus(Integer themeId, String userEmail) {
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isSubscribed = abonnementRepository.existsByUserIdAndThemeId(user.getId(), themeId);

        if (isSubscribed) {
            AbonnementModel abonnement = abonnementRepository.findByUserId(user.getId()).stream()
                    .filter(a -> a.getTheme().getId().equals(themeId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Subscription not found"));

            abonnementRepository.delete(abonnement);

        } else {
            ThemeModel theme = themeRepository.findById(themeId)
                    .orElseThrow(() -> new RuntimeException("Theme not found"));
            AbonnementModel abonnement = new AbonnementModel();
            abonnement.setUser(user);
            abonnement.setTheme(theme);
            abonnementRepository.save(abonnement);

        }
        SubscriptionStatusDTO status = new SubscriptionStatusDTO();
        status.setSubscribed(!isSubscribed);
        return status;
    }

}
