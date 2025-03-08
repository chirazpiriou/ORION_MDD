package com.openclassrooms.mddapi.services.Interfaces;

import java.util.List;
import com.openclassrooms.mddapi.dto.ArticleDTO;


public interface IArticleService {

    ArticleDTO getArticleById(Integer id);

    List<ArticleDTO> getAllArticlesForUser(String userEmail);

    void postArticle(ArticleDTO articleDTO, String userEmail);
}