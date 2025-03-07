package com.openclassrooms.mddapi.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "THEMES")
public class ThemeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "theme", nullable = false)
    private String theme;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "theme")
    private List<ArticleModel> articles;

    @OneToMany(mappedBy = "theme")
    private List<AbonnementModel> abonnements;

    
}
