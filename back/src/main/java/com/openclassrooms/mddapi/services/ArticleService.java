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

        if (article != null) {
            // Map the ArticleModel to ArticleDTO.
            ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);

            // Fetch and set the theme of the article, if available.
            Optional<ThemeModel> theme = themeRepository.findById(article.getThemeId());
            theme.ifPresent(themeModel -> {
                ThemeDTO themeDTO = modelMapper.map(themeModel, ThemeDTO.class);
                articleDTO.setTheme(themeDTO.getTheme());
            });

            // Fetch and set the author's name for the article.
            UserModel author = userRepository.findById(article.getAuteur_id()).orElseThrow();
            articleDTO.setAuteur(author.getName());

            // Fetch all comments associated with the article and map them to DTOs.
            List<CommentModel> comments = commentRepository.findAllByArticleId(article.getId());
            List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
                CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

                // Fetch and set the author's name for each comment.
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
        return null;
    }

    @Override
    public List<ArticleDTO> getAllArticlesForUser(String userEmail) {

        UserModel userModel = this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer userId = userModel.getId();

        List<Integer> themeIds = this.abonnementRepository.findAllByUserId(userId)
                .stream()
                .map(AbonnementModel::getThemeId)
                .collect(Collectors.toList());

        List<ArticleModel> articleModels = this.articleRepository.findAllByThemeIdIn(themeIds);

        List<ArticleDTO> articleDTOs = articleModels.stream()
                .map(article -> {
                    ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
                    articleDTO.setCreatedAt(article.getCreated_at());

                    UserModel authorModel = this.userRepository.findById(article.getAuteur_id())
                            .orElseThrow(() -> new RuntimeException("Author not found"));
                    articleDTO.setAuteur(authorModel.getName());

                    ThemeModel themeModel = this.themeRepository.findById(article.getThemeId())
                            .orElseThrow(() -> new RuntimeException("Theme not found"));
                    articleDTO.setTheme(themeModel.getTheme());

                    return articleDTO;
                })
                .collect(Collectors.toList());
        return articleDTOs;
    }

    public ArticleDTO postArticle(ArticleDTO articleDTO, String userEmail) {
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email"));
        if (articleDTO.getContenu() == null ||
                articleDTO.getTitre() == null) {
            ArticleDTO errorResponse = new ArticleDTO();
            return errorResponse;
        }
        ArticleModel articleCreated = modelMapper.map(articleDTO, ArticleModel.class);
        articleCreated.setCreatedAtToNow();
        String theme_name = articleDTO.getTheme();
        ThemeModel theme = themeRepository.findByTheme(theme_name);
        Integer theme_id = theme.getId();
        articleCreated.setThemeId(theme_id);
        articleCreated.setAuteur_id(user.getId());
        ArticleModel articleModelSaved = articleRepository.save(articleCreated);
        ArticleDTO articleResponse = modelMapper.map(articleModelSaved, ArticleDTO.class);
        return articleResponse;

    }

}
