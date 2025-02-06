package com.openclassrooms.mddapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data // Lombok annotation that generates getters, setters, toString, equals, and
      // hashCode methods automatically.
@Entity // Marks this class as a JPA entity to be mapped to a database table.
@Table(name = "ABONNEMENTS") // Specifies the name of the table in the database that this entity will be
                             // mapped to.
public class AbonnementModel {

    @Id // Denotes the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the primary key value using the IDENTITY
                                                        // strategy (e.g., auto-increment in the database).
    private Integer id;

    @Column(name = "theme_id") // Maps this field to the 'theme_id' column in the database table.
    private Integer themeId;

    @Column(name = "user_id") // Maps this field to the 'user_id' column in the database table.
    private Integer userId;

    // Constructor with parameters
    // This constructor allows you to create an instance of AbonnementModel with
    // specific themeId and userId.
    public AbonnementModel(Integer themeId, Integer userId) {
        this.themeId = themeId;
        this.userId = userId;
    }

    // Default constructor (required for JPA)
    // JPA requires a no-argument constructor to be present to instantiate the
    // entity.
    public AbonnementModel() {
    }
}
