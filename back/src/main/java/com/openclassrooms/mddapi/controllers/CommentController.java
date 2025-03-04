package com.openclassrooms.mddapi.controllers;

import javax.validation.Valid; // Ensures the CommentDTO object is validated.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.services.Interfaces.ICommentService;

/**
 * The CommentController class handles API requests related to comments.
 * It provides an endpoint for posting new comments.
 */
@RestController // Marks the class as a REST controller to handle HTTP requests and return JSON
                // responses.
@RequestMapping("/api/commentaire") // Specifies the base URL for comment-related API endpoints (e.g.,
                                    // /api/commentaire).
public class CommentController {

    @Autowired
    private ICommentService commentService; // Autowires the service to handle comment-related logic.

    /**
     * Endpoint to post a new comment.
     * The comment is validated and associated with the authenticated user's email.
     *
     * @param commentDTO     The comment data transferred from the client.
     * @param authentication The current user's authentication object, which
     *                       contains the user's email.
     * @return A response entity with the created comment, or an error message if
     *         the request is invalid.
     */
    @ResponseBody
    @PostMapping("/create") // Handles POST requests to the /create endpoint.
    public ResponseEntity<?> postComment(@Valid @RequestBody CommentDTO commentDTO, Authentication authentication) {
        String userEmail = authentication.getName(); // Retrieves the user's email from the authentication object.
        CommentDTO comment_DTO = commentService.postComment(commentDTO, userEmail); // Calls the service to create the
                                                                                    // comment.

        if (comment_DTO == null) {
            // If the comment creation failed, return a 400 Bad Request response with an
            // error message.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }

        // If the comment is successfully created, return the comment as a 200 OK
        // response.
        return ResponseEntity.ok(comment_DTO);
    }
}
