package com.openclassrooms.mddapi.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;
    private String contenu;
    private String user_str;
    @JsonProperty("article_id")
    private Integer article_id;
    private Timestamp createdAt;

}
