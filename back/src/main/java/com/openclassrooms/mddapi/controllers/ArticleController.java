package com.openclassrooms.mddapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.services.Interfaces.IArticleService;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticleById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllArticlesForUser(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getAllArticlesForUser(userEmail));
    }

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<?> postArticle(@Valid @RequestBody ArticleDTO articleRequestDTO, Authentication authentication) {
        String userEmail = authentication.getName();
        ArticleDTO articleResponseDTO = articleService.postArticle(articleRequestDTO, userEmail);
        if (articleResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(articleResponseDTO);
        } else {
            return ResponseEntity.ok(articleResponseDTO);
        }
    }

}
