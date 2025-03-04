package com.openclassrooms.mddapi.services.Interfaces;

import com.openclassrooms.mddapi.dto.CommentDTO;

/**
 * Interface for the Comment Service.
 * Defines the method for handling comment-related operations.
 */
public interface ICommentService {

    /**
     * Posts a comment and associates it with a specific user.
     *
     * @param commentDTO The data transfer object containing the comment details.
     * @param userEmail  The email of the user posting the comment.
     * @return The saved CommentDTO object after it has been processed and stored.
     */
    CommentDTO postComment(CommentDTO commentDTO, String userEmail);
}
