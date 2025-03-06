package com.openclassrooms.mddapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.models.CommentModel;

public interface CommentRepository extends JpaRepository<CommentModel, Integer> {
    List<CommentModel> findByArticleId(Integer articleId);
}