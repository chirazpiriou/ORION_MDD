package com.openclassrooms.mddapi.services.Interfaces;

import com.openclassrooms.mddapi.dto.CommentDTO;

public interface ICommentService {
    // Method to post a comment. It takes a CommentDTO object containing the comment details
    // and a userEmail to associate the comment with a specific user.
    CommentDTO postComment(CommentDTO commentDTO, String userEmail);
}
