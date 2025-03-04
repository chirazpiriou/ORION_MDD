package com.openclassrooms.mddapi.services;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.models.CommentModel;
import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.Interfaces.ICommentService;

/**
 * Service class responsible for handling comment-related operations.
 */
@Service
public class CommentService implements ICommentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    /**
     * Configures the ModelMapper to map specific fields between CommentModel and CommentDTO.
     * This method is executed after the bean construction.
     */
    @PostConstruct
    public void configureModelMapper() {
        modelMapper.addMappings(new PropertyMap<CommentModel, CommentDTO>() {
            @Override
            protected void configure() {
                // Explicitly map properties that do not have matching names.
                map().setArticle_id(source.getArticleId());
                map().setCreatedAt(source.getCreated_at());
            }
        });
    }

    /**
     * Posts a comment for a given article.
     *
     * @param commentDTO The comment data transfer object containing user input.
     * @param userEmail  The email of the authenticated user posting the comment.
     * @return The saved comment as a DTO.
     */
    @Override
    public CommentDTO postComment(CommentDTO commentDTO, String userEmail) {
        // Validate user existence using the provided email.
        UserModel userModel = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email"));

        // Validate article existence using the article ID from the request.
        articleRepository.findById(commentDTO.getArticle_id())
                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + commentDTO.getArticle_id()));

        // Map the CommentDTO to CommentModel.
        CommentModel commentModel = modelMapper.map(commentDTO, CommentModel.class);

        // Set additional fields in the comment model before saving it to the database.
        commentModel.setArticleId(commentDTO.getArticle_id());
        commentModel.setCreated_atNow(); // Assuming this method sets the current timestamp.
        commentModel.setAuteur_id(userModel.getId()); // Set the user ID of the author.

        // Save the comment into the database.
        CommentModel savedComment = commentRepository.save(commentModel);

        // Map the saved comment model to CommentDTO for the response.
        CommentDTO comment_DTO = modelMapper.map(savedComment, CommentDTO.class);

        // Set the author's name in the DTO.
        comment_DTO.setUser_str(userModel.getName());

        // Return the response DTO with the details of the posted comment.
        return comment_DTO;
    }
}
