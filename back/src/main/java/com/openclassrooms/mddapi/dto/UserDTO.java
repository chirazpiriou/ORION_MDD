package com.openclassrooms.mddapi.dto;

import java.security.Timestamp;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for the User model.
 * This class is used to transfer user data between different layers of the application (e.g., API responses).
 */
@Data // Lombok annotation that generates getters, setters, toString, equals, and hashCode methods automatically.
public class UserDTO {

    private Integer id; // Represents the ID of the user.

    private String email; // Represents the email of the user.

    private String name; // Represents the name of the user.

    private Timestamp created_at; // Represents the timestamp when the user was created in the system.
}
