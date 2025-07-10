package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.CommentTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentTaskRepo extends JpaRepository<CommentTask, Long> {
    List<CommentTask> findByTaskId(Long taskId);
}
