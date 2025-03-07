package com.openclassrooms.mddapi.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.models.ArticleModel;
import com.openclassrooms.mddapi.models.CommentModel;
import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.Interfaces.ICommentService;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Posts a comment for a specific article by a user.
     * 
     * @param commentDTO The comment details to be posted.
     * @param userEmail  The email of the user posting the comment.
     * @return The {@link CommentDTO} with the comment's details, including the
     *         generated ID and creation timestamp.
     * @throws UsernameNotFoundException if the user with the given email is not
     *                                   found.
     * @throws RuntimeException          if the article is not found.
     */

    @Override
    @Transactional
    // annotation ensures that the comment creation is performed in a single
    // transaction.
    // If an error occurs, the transaction is rolled back
    public CommentDTO postComment(CommentDTO commentDTO, String userEmail) {
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + userEmail));
        ArticleModel article = articleRepository.findById(commentDTO.getArticleId())
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
        CommentModel comment = new CommentModel();
        comment.setContenu(commentDTO.getContenu());
        comment.setUser(user);
        comment.setArticle(article);
        comment.setCreatedAt(LocalDateTime.now());
        comment = commentRepository.save(comment);
        commentDTO.setId(comment.getId());
        commentDTO.setCreatedAt(comment.getCreatedAt().toString());
        commentDTO.setUserName(user.getName());
        return commentDTO;
    }

    /**
     * Retrieves all comments for a specific article.
     * 
     * @param articleId The ID of the article for which comments are being
     *                  retrieved.
     * @return A list of {@link CommentDTO} representing all comments associated
     *         with the article.
     */

    @Override
    public List<CommentDTO> getAllArticleComments(Integer articleId) {
        List<CommentModel> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setId(comment.getId());
                    commentDTO.setContenu(comment.getContenu());
                    commentDTO.setUserId(comment.getUser().getId());
                    commentDTO.setUserName(comment.getUser().getName());
                    commentDTO.setArticleId(comment.getArticle().getId());
                    commentDTO.setCreatedAt(comment.getCreatedAt().toString());
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }
}
