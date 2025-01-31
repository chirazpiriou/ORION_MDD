package com.openclassrooms.mddapi.services.Interfaces;

import java.util.List;

import com.openclassrooms.mddapi.dto.ArticleDTO;

public interface IArticleService {

    List<ArticleDTO> getAllArticlesForUser(String userEmail);

    ArticleDTO getArticleById(Integer id);

    ArticleDTO postArticle(ArticleDTO articleDTO, String userEmail);

}
