package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.models.AbonnementModel;
import com.openclassrooms.mddapi.models.ArticleModel;
import com.openclassrooms.mddapi.models.CommentModel;
import com.openclassrooms.mddapi.models.ThemeModel;
import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.AbonnementRepository;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.Interfaces.IArticleService;

/**
 * Service class for managing articles.
 */
@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves an article by its ID, including its theme, author, and comments.
     *
     * @param id The ID of the article to retrieve.
     * @return An ArticleDTO containing the article details or null if not found.
     */
    public ArticleDTO getArticleById(Integer id) {
        // Retrieve the article by ID or throw an exception if not found.
        ArticleModel article = articleRepository.findById(id).orElseThrow();

        // Convert the ArticleModel entity to an ArticleDTO.
        ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);

        // Fetch and set the theme of the article.
        Optional<ThemeModel> theme = themeRepository.findById(article.getThemeId());
        theme.ifPresent(themeModel -> {
            ThemeDTO themeDTO = modelMapper.map(themeModel, ThemeDTO.class);
            articleDTO.setTheme(themeDTO.getTheme());
        });

        // Retrieve and set the author's name for the article.
        UserModel author = userRepository.findById(article.getAuteur_id()).orElseThrow();
        articleDTO.setAuteur(author.getName());

        // Retrieve all comments associated with the article and convert them to DTOs.
        List<CommentModel> comments = commentRepository.findAllByArticleId(article.getId());
        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
            CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

            // Retrieve and set the author's name for each comment.
            UserModel commentAuthor = userRepository.findById(comment.getAuteur_id()).orElseThrow();
            commentDTO.setUser_str(commentAuthor.getName());
            commentDTO.setCreatedAt(comment.getCreated_at());

            return commentDTO;
        }).collect(Collectors.toList());

        // Set the comments and creation date in the article DTO.
        articleDTO.setCommentaires(commentDTOs);
        articleDTO.setCreatedAt(article.getCreated_at());

        return articleDTO;
    }

    /**
     * Retrieves all articles for a given user based on their subscribed themes.
     *
     * @param userEmail The email of the user.
     * @return A list of ArticleDTOs for the user.
     */
    @Override
    public List<ArticleDTO> getAllArticlesForUser(String userEmail) {
        // Retrieve the user by email or throw an exception.
        UserModel userModel = this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer userId = userModel.getId();

        // Get all theme IDs the user is subscribed to.
        List<Integer> themeIds = this.abonnementRepository.findAllByUserId(userId)
                .stream()
                .map(AbonnementModel::getThemeId)
                .collect(Collectors.toList());

        // Retrieve all articles related to the subscribed themes.
        List<ArticleModel> articleModels = this.articleRepository.findAllByThemeIdIn(themeIds);

        // Convert articles to DTO format.
        return articleModels.stream()
                .map(article -> {
                    ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
                    articleDTO.setCreatedAt(article.getCreated_at());

                    // Retrieve and set the author's name.
                    UserModel authorModel = this.userRepository.findById(article.getAuteur_id())
                            .orElseThrow(() -> new RuntimeException("Author not found"));
                    articleDTO.setAuteur(authorModel.getName());

                    // Retrieve and set the theme name.
                    ThemeModel themeModel = this.themeRepository.findById(article.getThemeId())
                            .orElseThrow(() -> new RuntimeException("Theme not found"));
                    articleDTO.setTheme(themeModel.getTheme());

                    return articleDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Creates and posts a new article for a given user.
     *
     * @param articleDTO The article data to post.
     * @param userEmail  The email of the author.
     * @return The saved article as an ArticleDTO.
     */
    public ArticleDTO postArticle(ArticleDTO articleDTO, String userEmail) {
        // Retrieve the user by email or throw an exception.
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email"));

        // Validate that the article contains a title and content.
        if (articleDTO.getContenu() == null || articleDTO.getTitre() == null) {
            return new ArticleDTO(); // Returns an empty response if validation fails.
        }

        // Convert the DTO to an entity model.
        ArticleModel articleCreated = modelMapper.map(articleDTO, ArticleModel.class);
        articleCreated.setCreatedAtToNow();

        // Retrieve the theme model based on the theme name.
        ThemeModel theme = themeRepository.findByTheme(articleDTO.getTheme());
        Integer themeId = theme.getId();

        // Assign the retrieved theme ID and author ID to the article.
        articleCreated.setThemeId(themeId);
        articleCreated.setAuteur_id(user.getId());

        // Save the article in the database.
        ArticleModel articleModelSaved = articleRepository.save(articleCreated);

        // Convert the saved entity back to DTO format.
        return modelMapper.map(articleModelSaved, ArticleDTO.class);
    }
}
