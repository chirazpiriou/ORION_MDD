package com.openclassrooms.mddapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.openclassrooms.mddapi.models.ArticleModel;

public interface ArticleRepository extends CrudRepository<ArticleModel, Integer> {
    List<ArticleModel> findAllByThemeIdIn(List<Integer> themeIds);

}