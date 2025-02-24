package com.openclassrooms.mddapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.services.Interfaces.ICommentService;

@RestController
@RequestMapping("/api/commentaire")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<?> postComment(@Valid @RequestBody CommentDTO commentDTO, Authentication authentication) {
        String userEmail = authentication.getName();
        CommentDTO comment_DTO = commentService.postComment(commentDTO, userEmail);
        if (comment_DTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
        return ResponseEntity.ok(comment_DTO);
    }

}
