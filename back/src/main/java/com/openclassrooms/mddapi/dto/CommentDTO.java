package com.openclassrooms.mddapi.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for the Comment model.
 * This class is used to transfer comment-related data between different layers
 * of the application (e.g., API responses).
 */
@Data // Lombok annotation that generates getters, setters, toString, equals, and
      // hashCode methods automatically.
public class CommentDTO {

    private Integer id; // Represents the ID of the comment.

    private String contenu; // Represents the content or body of the comment.

    private String user_str; // Represents the username or string associated with the user who made the
                             // comment.

    @JsonProperty("article_id") // Maps the field to the 'article_id' key in the JSON, ensuring consistency in
                                // requests.
    private Integer article_id; // Represents the ID of the article the comment is associated with.

    private Timestamp createdAt; // Represents the timestamp when the comment was created.
}
