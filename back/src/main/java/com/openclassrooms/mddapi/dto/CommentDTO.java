package com.openclassrooms.mddapi.dto;


import lombok.Data;


@Data 
public class CommentDTO {

    private Integer id;
    private String contenu;
    private Integer userId;      // Correspond à l'id de l'utilisateur (User)
    private Integer articleId;   // Correspond à l'id de l'article (Article)
    private String createdAt;
}
