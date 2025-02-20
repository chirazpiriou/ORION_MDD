package com.openclassrooms.mddapi.models;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "COMMENTAIRES")
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String contenu;
    @Column(name = "article_id")
    private Integer articleId;
    @Column(name = "user_id")
    private Integer auteur_id;
    public Timestamp created_at;
    
    public void setCreated_atNow() {
        Timestamp now = Timestamp.from(Instant.now());
        this.setCreated_at(now);
    }

}
