package com.openclassrooms.mddapi.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.openclassrooms.mddapi.models.ThemeModel;

/**
 * Repository interface for ThemeModel.
 * Extends CrudRepository to provide basic CRUD operations for ThemeModel.
 */
public interface ThemeRepository extends CrudRepository<ThemeModel, Integer> {

    /**
     * Finds a theme by its name.
     *
     * @param theme The name of the theme to find.
     * @return The ThemeModel object matching the theme name, or null if not found.
     */
    ThemeModel findByTheme(String theme);

    /**
     * Retrieves all themes in the system.
     *
     * @return A list of all ThemeModel objects.
     */
    List<ThemeModel> findAll();

    /**
     * Finds all themes with IDs in the provided list of subscribed theme IDs.
     *
     * @param subscribedThemeIds The list of theme IDs to search for.
     * @return A list of ThemeModel objects matching the IDs in the provided list.
     */
    List<ThemeModel> findAllByIdIn(List<Integer> subscribedThemeIds);
}
