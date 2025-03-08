package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private Integer id;
    private String contenu;
    private Integer userId;
    private String userName; 
    private Integer articleId; 
    private String createdAt;
}
