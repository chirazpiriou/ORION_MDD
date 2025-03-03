package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for the Theme model.
 * This class is used to transfer theme-related data between different layers of
 * the application (e.g., API responses).
 */
@Data // Lombok annotation that generates getters, setters, toString, equals, and
      // hashCode methods automatically.
public class ThemeDTO {

    private Integer id; // Represents the ID of the theme.

    private String theme; // Represents the name or title of the theme.

    private String description; // Represents a description of the theme.

    // Added @JsonProperty("isSubscribed") annotation to the isSubscribed field to
    // ensure that the corresponding JSON key remains 'isSubscribed' instead of
    // 'subscribed'.
    // Before this change, Postman interpreted the property incorrectly, potentially
    // causing inconsistencies in requests.
    @JsonProperty("isSubscribed") // Maps the field to the 'isSubscribed' key in the JSON, ensuring consistency in
                                  // requests.
    private boolean isSubscribed; // Represents whether the user is subscribed to the theme.
}
