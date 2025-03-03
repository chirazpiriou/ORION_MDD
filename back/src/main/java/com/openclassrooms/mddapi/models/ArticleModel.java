package com.openclassrooms.mddapi.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Represents the Article model, mapped to the "ARTICLES" table in the database.
 * This class stores details of articles created by users.
 */
@Entity // Marks this class as a JPA entity, mapped to a database table.
@Table(name = "ARTICLES") // Specifies that this class is mapped to the "ARTICLES" table in the database.
@Data // Automatically generates getters, setters, equals, hashCode, and toString
      // methods using Lombok.
public class ArticleModel {

    @Id // Marks this field as the primary key for the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates unique IDs for each article.
    private Integer id;

    private String titre; // Represents the title of the article.

    @Column(name = "theme_id") // Specifies the column name in the table as "theme_id".
    private Integer themeId; // Represents the ID of the theme the article belongs to.

    private Integer auteur_id; // Represents the ID of the user who authored the article.

    private String contenu; // Represents the content of the article.

    public Timestamp created_at; // Stores the timestamp when the article was created.

    /**
     * Sets the created_at field to the current timestamp.
     * This method generates a timestamp based on the current system time.
     */
    public void setCreatedAtToNow() {
        // Creates a Timestamp object from the current time in milliseconds and sets it.
        this.created_at = new Timestamp(System.currentTimeMillis());
    }
}
