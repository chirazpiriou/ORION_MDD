package com.openclassrooms.mddapi.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class ArticleDTO {
    private Integer id;
    private String auteur;
    private String theme;
    private String titre;
    private String contenu;
    private List<CommentDTO> commentaires;
    private Timestamp createdAt;

}
