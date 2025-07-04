package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepo  extends CrudRepository<Comment, Long> {
}
