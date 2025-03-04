package com.openclassrooms.mddapi.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.openclassrooms.mddapi.models.CommentModel;

/**
 * Repository interface for CommentModel.
 * Extends CrudRepository to provide basic CRUD operations for CommentModel.
 */
public interface CommentRepository extends CrudRepository<CommentModel, Integer> {

    /**
     * Finds all comments associated with a specific article.
     *
     * @param id The ID of the article for which comments are to be retrieved.
     * @return A list of CommentModel objects associated with the specified article
     *         ID.
     */
    List<CommentModel> findAllByArticleId(Integer id);
}
