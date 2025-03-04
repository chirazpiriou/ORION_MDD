package com.openclassrooms.mddapi.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for the Abonnement model.
 * This class is used to transfer subscription-related data between different
 * layers of the application (e.g., API responses).
 */
@Data // Lombok annotation that generates getters, setters, toString, equals, and
      // hashCode methods automatically.
public class AbonnementDTO {

    private Integer theme_id; // Represents the ID of the theme that the user is subscribed to.
}
