package com.openclassrooms.mddapi.services.Interfaces;

import java.util.List;
import com.openclassrooms.mddapi.dto.CommentDTO;

public interface ICommentService {

    CommentDTO postComment(CommentDTO commentDTO, String userEmail);

    List<CommentDTO> getAllArticleComments(Integer articleId);
}
