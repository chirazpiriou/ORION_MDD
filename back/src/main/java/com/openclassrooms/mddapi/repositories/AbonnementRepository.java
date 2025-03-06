package com.openclassrooms.mddapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.models.AbonnementModel;

public interface AbonnementRepository extends JpaRepository<AbonnementModel, Integer> {
    List<AbonnementModel> findByUserId(Integer userId);

    boolean existsByUserIdAndThemeId(Integer userId, Integer themeId);
}