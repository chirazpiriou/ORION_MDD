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

@Service
public class AbonnementService implements IAbonnementService {

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeRepository themeRepository;

    /**
     * Toggles the subscription status of a user for a specific theme.
     * 
     * <p>
     * This method checks if the user is currently subscribed to the specified
     * theme. If subscribed,
     * it unsubscribes the user by removing the subscription. If not subscribed,
     * it subscribes the user to the theme.
     * </p>
     * 
     * @param themeId   The ID of the theme to change the subscription status for.
     * @param userEmail The email of the user whose subscription status is being
     *                  changed.
     * @return A {@link SubscriptionStatusDTO} indicating the new subscription
     *         status.
     * @throws RuntimeException if the user or theme is not found, or if the
     *                          subscription is not found when unsubscribing.
     * @Transactional Ensures that the operation is completed in a single
     *                transaction.
     */

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
