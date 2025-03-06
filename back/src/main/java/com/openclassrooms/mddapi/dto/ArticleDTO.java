package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class ArticleDTO {

    private Integer id;
    private String titre;
    private String contenu;
    private Integer auteurId;
    private Integer themeId;
    private String createdAt;
}
