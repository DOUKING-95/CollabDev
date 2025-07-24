package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends CrudRepository<Task, Long> {
}
