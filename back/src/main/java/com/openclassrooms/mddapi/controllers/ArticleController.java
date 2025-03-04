package com.openclassrooms.mddapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.services.Interfaces.IArticleService;

/**
 * ArticleController handles the endpoints related to articles, such as
 * retrieving article details,
 * fetching all articles for a user, and posting new articles.
 */
@RestController // Marks the class as a Spring REST controller that handles HTTP requests and
                // returns JSON responses.
@RequestMapping("/api/article") // Specifies the base URL for all article-related API endpoints.
public class ArticleController {

    @Autowired
    private IArticleService articleService; // Autowires the IArticleService to handle article-related business logic.

    /**
     * Endpoint to get a specific article by its ID.
     * 
     * @param id The ID of the article to be retrieved.
     * @return A response entity containing the article details if found, or an
     *         error message if not.
     */
    @GetMapping("/detail/{id}") // Maps GET requests to retrieve article details by its ID.
    public ResponseEntity<?> getArticleById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticleById(id)); // Retrieves the article by
                                                                                             // ID.
    }

    /**
     * Endpoint to get all articles for the authenticated user.
     * 
     * @param authentication The authentication object to get the currently
     *                       authenticated user's email.
     * @return A response entity containing all articles associated with the user.
     */
    @GetMapping("/all") // Maps GET requests to retrieve all articles for the authenticated user.
    public ResponseEntity<?> getAllArticlesForUser(Authentication authentication) {
        String userEmail = authentication.getName(); // Retrieves the authenticated user's email.
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getAllArticlesForUser(userEmail)); // Retrieves
                                                                                                           // the
                                                                                                           // articles.
    }

    /**
     * Endpoint to post a new article.
     * 
     * @param articleRequestDTO The article data submitted in the request body.
     * @param authentication    The authentication object to get the email of the
     *                          authenticated user.
     * @return A response entity containing the created article or a bad request
     *         message if something goes wrong.
     */
    @ResponseBody
    @PostMapping("/create") // Maps POST requests to create a new article.
    public ResponseEntity<?> postArticle(@Valid @RequestBody ArticleDTO articleRequestDTO,
            Authentication authentication) {
        String userEmail = authentication.getName(); // Retrieves the authenticated user's email.
        ArticleDTO articleResponseDTO = articleService.postArticle(articleRequestDTO, userEmail); // Calls the service
                                                                                                  // to create the
                                                                                                  // article.

        // If the article creation failed (e.g., due to invalid data), return a bad
        // request response.
        if (articleResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(articleResponseDTO);
        } else {
            return ResponseEntity.ok(articleResponseDTO); // Returns the created article.
        }
    }

}
