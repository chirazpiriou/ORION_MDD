package com.openclassrooms.mddapi.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for the Article model.
 * This class is used to transfer article-related data between different layers
 * of the application (e.g., API responses).
 */
@Data // Lombok annotation that generates getters, setters, toString, equals, and
      // hashCode methods automatically.
public class ArticleDTO {

    private Integer id; // Represents the ID of the article.

    private String auteur; // Represents the author of the article.

    private String theme; // Represents the theme or category of the article.

    private String titre; // Represents the title of the article.

    private String contenu; // Represents the content or body of the article.

    private List<CommentDTO> commentaires; // Represents a list of comments associated with the article. It is a list of
                                           // CommentDTO objects.

    private Timestamp createdAt; // Represents the timestamp when the article was created.
}
