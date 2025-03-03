package com.openclassrooms.mddapi.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.openclassrooms.mddapi.models.ArticleModel;

/**
 * Repository interface for ArticleModel.
 * Extends CrudRepository to provide basic CRUD operations for ArticleModel.
 */
public interface ArticleRepository extends CrudRepository<ArticleModel, Integer> {

    /**
     * Finds all articles associated with specific theme IDs.
     *
     * @param themeIds A list of theme IDs for which articles are to be retrieved.
     * @return A list of ArticleModel objects that belong to the specified themes.
     */
    List<ArticleModel> findAllByThemeIdIn(List<Integer> themeIds);
}
