package com.openclassrooms.mddapi.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.services.Interfaces.ICommentService;

@RestController
@RequestMapping("/api/commentaire")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @PostMapping("/create")
    public CommentDTO postComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
        String userEmail = authentication.getName();
        return commentService.postComment(commentDTO, userEmail);
    }

    @GetMapping("/article/{articleId}")
    public List<CommentDTO> getAllArticleComments(@PathVariable Integer articleId) {
        return commentService.getAllArticleComments(articleId);
    }
}
