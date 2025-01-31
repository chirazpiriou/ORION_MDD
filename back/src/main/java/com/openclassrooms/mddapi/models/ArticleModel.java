package com.openclassrooms.mddapi.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ARTICLES")
@Data
public class ArticleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titre;
    @Column(name = "theme_id")
    private Integer themeId;
    private Integer auteur_id;
    private String contenu;
    public Timestamp created_at;

    public void setCreatedAtToNow() {
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

}
