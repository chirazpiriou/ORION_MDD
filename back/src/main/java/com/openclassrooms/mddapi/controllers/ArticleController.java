package com.openclassrooms.mddapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.services.ArticleService;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Integer id, Authentication authentication) {

        ArticleDTO articleDTO = articleService.getArticleById(id);
        if (articleDTO != null) {
            return ResponseEntity.ok(articleDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArticleDTO>> getAllArticlesForUser(Authentication authentication) {
        String userEmail = getUserEmailFromAuthentication(authentication);
        List<ArticleDTO> articles = articleService.getAllArticlesForUser(userEmail);
        return ResponseEntity.ok(articles);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> postArticle(@RequestBody ArticleDTO articleDTO,
            Authentication authentication) {
        String userEmail = getUserEmailFromAuthentication(authentication);
        articleService.postArticle(articleDTO, userEmail);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Article created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private String getUserEmailFromAuthentication(Authentication authentication) {
        return authentication.getName();
    }

}
