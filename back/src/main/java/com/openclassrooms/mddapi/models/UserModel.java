package com.openclassrooms.mddapi.models;

import java.security.Principal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 * UserModel represents a user entity in the system.
 * This class is mapped to the "USERS" table in the database.
 * It implements the Principal interface to be used as a security context user.
 */
@Entity
@Table(name = "USERS") // Maps the class to the "USERS" table in the database.
@Data // Generates getters, setters, equals, hashCode, and toString methods
      // automatically using Lombok.
public class UserModel implements Principal {

    @Id // Marks this field as the primary key in the database.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates unique IDs for the user.
    private Integer id;

    @Column(unique = true) // Ensures that the email is unique in the database.
    private String email;

    private String name; // Represents the user's name.

    private String password; // Represents the user's password.

    @CreationTimestamp // Automatically sets the creation timestamp when the user is created.
    private Timestamp created_at; // Represents the timestamp when the user was created.

    /**
     * Returns the name of the user.
     * This method is part of the Principal interface.
     * 
     * @return The name of the user.
     */
    @Override
    public String getName() {
        return name;
    }
}
