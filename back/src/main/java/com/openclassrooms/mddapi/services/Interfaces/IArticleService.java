package com.openclassrooms.mddapi.services.Interfaces;

import java.util.List;
import com.openclassrooms.mddapi.dto.ArticleDTO;

/**
 * Interface for the Article Service.
 * Defines methods related to article-related operations.
 */
public interface IArticleService {

    /**
     * Retrieves all articles associated with a specific user.
     *
     * @param userEmail The email of the user whose articles are being fetched.
     * @return A list of ArticleDTO objects representing the user's articles.
     */
    List<ArticleDTO> getAllArticlesForUser(String userEmail);

    /**
     * Retrieves an article by its unique identifier.
     *
     * @param id The ID of the article to retrieve.
     * @return An ArticleDTO object representing the article with the specified ID.
     */
    ArticleDTO getArticleById(Integer id);

    /**
     * Posts a new article and associates it with a specific user.
     *
     * @param articleDTO The data transfer object containing the article's details.
     * @param userEmail  The email of the user posting the article.
     * @return The saved ArticleDTO object after it has been processed and stored.
     */
    ArticleDTO postArticle(ArticleDTO articleDTO, String userEmail);
}
