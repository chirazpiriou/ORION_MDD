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

    @Override
    @Transactional
    public CommentDTO postComment(CommentDTO commentDTO, String userEmail) {
   
        UserModel user = userRepository.findByEmail(userEmail)
        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + userEmail));

        ArticleModel article = articleRepository.findById(commentDTO.getArticleId()).orElseThrow(() -> new RuntimeException("Article non trouvé"));

        CommentModel comment = new CommentModel();
        comment.setContenu(commentDTO.getContenu());
        comment.setUser(user);
        comment.setArticle(article);
        comment.setCreatedAt(LocalDateTime.now()); 

  
        comment = commentRepository.save(comment);

        commentDTO.setId(comment.getId());
        commentDTO.setCreatedAt(comment.getCreatedAt().toString());

        return commentDTO;
    }

    @Override
    public List<CommentDTO> getAllArticleComments(Integer articleId) {
        List<CommentModel> comments = commentRepository.findByArticleId(articleId);

        return comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setId(comment.getId());
                    commentDTO.setContenu(comment.getContenu());
                    commentDTO.setUserId(comment.getUser().getId());
                    commentDTO.setArticleId(comment.getArticle().getId());
                    commentDTO.setCreatedAt(comment.getCreatedAt().toString());
                    return commentDTO;
                })
                .collect(Collectors.toList());
    }
}
