package com.openclassrooms.mddapi.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.openclassrooms.mddapi.models.AbonnementModel;

/**
 * Repository interface for AbonnementModel.
 * Extends CrudRepository to provide basic CRUD operations for AbonnementModel.
 */
public interface AbonnementRepository extends CrudRepository<AbonnementModel, Integer> {

    /**
     * Finds all subscriptions (AbonnementModel) associated with a specific user.
     *
     * @param userId The ID of the user for whom subscriptions are to be retrieved.
     * @return A list of AbonnementModel objects associated with the specified user ID.
     */
    List<AbonnementModel> findAllByUserId(Integer userId);

    /**
     * Finds a specific subscription by the user ID and theme ID.
     *
     * @param user_id The ID of the user whose subscription is being searched.
     * @param theme_id The ID of the theme to check for the user's subscription.
     * @return An Optional containing the AbonnementModel if found, or empty if not found.
     */
    Optional<AbonnementModel> findByUserIdAndThemeId(Integer user_id, Integer theme_id);
}
