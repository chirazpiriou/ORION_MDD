package com.openclassrooms.mddapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.openclassrooms.mddapi.models.CommentModel;

public interface CommentRepository extends CrudRepository<CommentModel, Integer> {

    List<CommentModel> findAllByArticleId(Integer id);
}
