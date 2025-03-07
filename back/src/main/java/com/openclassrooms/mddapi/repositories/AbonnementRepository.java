package com.openclassrooms.mddapi.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassrooms.mddapi.models.AbonnementModel;
import com.openclassrooms.mddapi.models.UserModel;

public interface AbonnementRepository extends JpaRepository<AbonnementModel, Integer> {

    List<AbonnementModel> findByUserId(Integer userId);

    List<AbonnementModel> findByUser(UserModel user);

    boolean existsByUserIdAndThemeId(Integer userId, Integer themeId);
}