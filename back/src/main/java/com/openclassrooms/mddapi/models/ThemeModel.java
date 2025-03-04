package com.openclassrooms.mddapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Represents the Theme model, mapped to the "THEMES" table in the database.
 * This class stores information about different themes.
 */
@Data // Automatically generates getters, setters, equals, hashCode, and toString
      // methods using Lombok.
@Entity // Marks this class as a JPA entity, to be mapped to a database table.
@Table(name = "THEMES") // Specifies that this class is mapped to the "THEMES" table in the database.
public class ThemeModel {

    @Id // Marks this field as the primary key for the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates unique IDs for each theme.
    private Integer id;

    private String theme; // Represents the name of the theme.

    private String description; // Provides a description for the theme.
}
