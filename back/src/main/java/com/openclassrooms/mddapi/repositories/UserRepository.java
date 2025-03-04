package com.openclassrooms.mddapi.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.openclassrooms.mddapi.models.UserModel;

/**
 * Repository interface for UserModel.
 * Extends CrudRepository to provide basic CRUD operations for UserModel.
 */
public interface UserRepository extends CrudRepository<UserModel, Integer> {

    /**
     * Finds a user by their email.
     *
     * @param email The email of the user to find.
     * @return An Optional containing the UserModel if found, or empty if not.
     */
    Optional<UserModel> findByEmail(String email);

    /**
     * Finds a user by their name.
     *
     * @param name The name of the user to find.
     * @return An Optional containing the UserModel if found, or empty if not.
     */
    Optional<UserModel> findByName(String name);

    /**
     * Finds a user by their unique ID.
     *
     * @param id The ID of the user to find.
     * @return An Optional containing the UserModel if found, or empty if not.
     */
    Optional<UserModel> findById(Integer id);
}
