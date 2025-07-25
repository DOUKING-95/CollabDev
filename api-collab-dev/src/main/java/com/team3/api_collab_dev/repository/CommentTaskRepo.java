package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.CommentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentTaskRepo extends JpaRepository<CommentTask, Long> {
    List<CommentTask> findByTaskId(Long taskId);
}
