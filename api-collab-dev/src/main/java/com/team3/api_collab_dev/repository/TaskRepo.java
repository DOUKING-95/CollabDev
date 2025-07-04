package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepo extends CrudRepository<Task, Long> {
}
