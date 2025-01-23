package com.openclassrooms.mddapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ABONNEMENTS")
public class AbonnementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "theme_id")
    private Integer themeId;
    @Column(name = "user_id")
    private Integer userId;
    
}
