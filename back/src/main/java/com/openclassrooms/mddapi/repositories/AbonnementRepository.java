package com.openclassrooms.mddapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.openclassrooms.mddapi.models.AbonnementModel;

public interface AbonnementRepository extends CrudRepository<AbonnementModel, Integer> {
    List<AbonnementModel> findAllByUserId(Integer userId);

    Optional<AbonnementModel> findByUserIdAndThemeId(Integer user_id, Integer theme_id);

}