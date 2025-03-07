package com.openclassrooms.mddapi.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.models.ArticleModel;
import com.openclassrooms.mddapi.models.ThemeModel;
import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.AbonnementRepository;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.Interfaces.IArticleService;

@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private AbonnementRepository abonnementRepository;

    /**
     * Retrieves an article by its ID.
     * 
     * <p>
     * This method fetches an article's details by its ID and returns them in an
     * {@link ArticleDTO}.
     * </p>
     * 
     * @param id The ID of the article to be retrieved.
     * @return A {@link ArticleDTO} containing the article's details (ID, title,
     *         content, author, theme, and creation date).
     * @throws RuntimeException if the article with the given ID is not found.
     */

    @Override
    public ArticleDTO getArticleById(Integer id) {
        ArticleModel article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article non trouvé avec l'ID: " + id));
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitre(article.getTitre());
        articleDTO.setContenu(article.getContenu());
        articleDTO.setAuteur(article.getAuteur().getName());
        articleDTO.setTheme(article.getTheme().getTheme());
        articleDTO.setCreatedAt(article.getCreatedAt().toString());
        return articleDTO;
    }

    /**
     * Retrieves all articles for a user based on their subscribed themes.
     * 
     * <p>
     * This method fetches the user's subscribed themes, then retrieves all articles
     * associated with those themes and returns them in a list of
     * {@link ArticleDTO}.
     * </p>
     * 
     * @param userEmail The email of the user for whom the articles are being
     *                  retrieved.
     * @return A list of {@link ArticleDTO} containing the details (ID, title,
     *         content, author, theme, and creation date) of the articles related to
     *         the user's subscribed themes.
     * @throws RuntimeException         if the user with the given email is not
     *                                  found.
     * @throws IllegalArgumentException if the user is not found with the specified
     *                                  email.
     */

    @Override
    public List<ArticleDTO> getAllArticlesForUser(String userEmail) {
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'email: " + userEmail);
        }
        List<Integer> themeIds = abonnementRepository.findByUserId(user.getId())
                .stream()
                .map(abonnement -> abonnement.getTheme().getId())
                .collect(Collectors.toList());
        List<ArticleModel> articles = articleRepository.findByThemeIdIn(themeIds);
        return articles.stream()
                .map(article -> {
                    ArticleDTO articleDTO = new ArticleDTO();
                    articleDTO.setId(article.getId());
                    articleDTO.setTitre(article.getTitre());
                    articleDTO.setContenu(article.getContenu());
                    articleDTO.setAuteur(article.getAuteur().getName());
                    articleDTO.setTheme(article.getTheme().getTheme());
                    articleDTO.setCreatedAt(article.getCreatedAt().toString());
                    return articleDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Creates and posts a new article.
     * 
     * <p>
     * This method takes an {@link ArticleDTO} containing the article's title,
     * content, and theme,
     * associates it with the user (author) and the selected theme, and saves it to
     * the article repository.
     * </p>
     * 
     * @param articleDTO The details of the article to be posted, including title,
     *                   content, and theme.
     * @param userEmail  The email of the user posting the article (author).
     * @throws RuntimeException if the user or theme is not found.
     */

    @Override
    public void postArticle(ArticleDTO articleDTO, String userEmail) {
        ArticleModel article = new ArticleModel();

        UserModel auteur = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ThemeModel theme = themeRepository.findByTheme(articleDTO.getTheme())
                .orElseThrow(() -> new RuntimeException("Theme not found"));
        article.setTitre(articleDTO.getTitre());
        article.setContenu(articleDTO.getContenu());
        article.setAuteur(auteur);
        article.setTheme(theme);
        article.setCreatedAt(LocalDateTime.now());
        articleRepository.save(article);
    }
}
