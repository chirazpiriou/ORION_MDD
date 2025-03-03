package com.openclassrooms.mddapi.models;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Represents a Comment model, mapped to the "COMMENTAIRES" table in the
 * database.
 * This class stores details of comments made by users on articles.
 */
@Data // Automatically generates getters, setters, equals, hashCode, and toString
      // methods using Lombok.
@Entity // Marks this class as a JPA entity, to be mapped to a database table.
@Table(name = "COMMENTAIRES") // Specifies that this class is mapped to the "COMMENTAIRES" table in the
                              // database.
public class CommentModel {

    @Id // Marks this field as the primary key for the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates unique IDs for each comment.
    private Integer id;

    private String contenu; // Represents the content of the comment.

    @Column(name = "article_id") // Specifies the column name in the table as "article_id".
    private Integer articleId; // Represents the ID of the article that the comment belongs to.

    @Column(name = "user_id") // Specifies the column name in the table as "user_id".
    private Integer auteur_id; // Represents the ID of the user who authored the comment.

    public Timestamp created_at; // Stores the timestamp when the comment was created.

    /**
     * Sets the created_at field to the current timestamp.
     * This method generates a timestamp based on the current system time.
     */
    public void setCreated_atNow() {
        // Creates a Timestamp object from the current time.
        Timestamp now = Timestamp.from(Instant.now());
        // Sets the created_at field to the current timestamp.
        this.setCreated_at(now);
    }
}
