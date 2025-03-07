package com.openclassrooms.mddapi.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassrooms.mddapi.models.ArticleModel;


public interface ArticleRepository extends JpaRepository<ArticleModel, Integer> {
    
    List<ArticleModel> findByAuteurEmail(String auteurEmail);

    List<ArticleModel> findByThemeIdIn(List<Integer> themeIds);
  
    
}