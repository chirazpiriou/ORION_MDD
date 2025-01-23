package com.openclassrooms.mddapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.openclassrooms.mddapi.models.ThemeModel;

public interface  ThemeRepository extends CrudRepository<ThemeModel, Integer> {
    ThemeModel findByTheme(String theme);
    List<ThemeModel> findAll();
    List<ThemeModel> findAllByIdIn(List<Integer> subscribedThemeIds);
}
