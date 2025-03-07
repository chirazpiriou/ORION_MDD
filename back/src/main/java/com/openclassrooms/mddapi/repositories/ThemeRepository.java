package com.openclassrooms.mddapi.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassrooms.mddapi.models.ThemeModel;

public interface ThemeRepository extends JpaRepository<ThemeModel, Integer> {
    
    Optional<ThemeModel> findByTheme(String theme);
}