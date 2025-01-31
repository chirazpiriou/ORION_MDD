package com.openclassrooms.mddapi.dto;


import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;
    private String contenu;
    private String user_str;
    private Integer article_id;
    private Timestamp createdAt;

}
