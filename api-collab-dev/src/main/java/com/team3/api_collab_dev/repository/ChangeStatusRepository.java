package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.enumType.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangeStatusRepository extends JpaRepository<Project,Long> {
    List<Project> findByManagerIdAndStatus(Long managerId, Status status);

}
