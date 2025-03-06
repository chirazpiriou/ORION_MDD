package com.openclassrooms.mddapi.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.models.ArticleModel;
import com.openclassrooms.mddapi.models.ThemeModel;
import com.openclassrooms.mddapi.models.UserModel;
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
    private ModelMapper modelMapper;

    @Override
    public ArticleDTO getArticleById(Integer id) {
        Optional<ArticleModel> article = articleRepository.findById(id);
        return article.map(a -> modelMapper.map(a, ArticleDTO.class)).orElse(null); 
    }

    @Override
    public List<ArticleDTO> getAllArticlesForUser(String userEmail) {
        List<ArticleModel> articles = articleRepository.findByAuteurEmail(userEmail);
        return articles.stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class)) 
                .collect(Collectors.toList());
    }

    @Override
    public void postArticle(ArticleDTO articleDTO, String userEmail) {
        ArticleModel article = new ArticleModel();

        UserModel auteur = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ThemeModel theme = themeRepository.findById(articleDTO.getThemeId())
                .orElseThrow(() -> new RuntimeException("Theme not found"));

        article.setTitre(articleDTO.getTitre());
        article.setContenu(articleDTO.getContenu());
        article.setAuteur(auteur);
        article.setTheme(theme);
        article.setCreatedAt(LocalDateTime.now());

        articleRepository.save(article);
    }
}
