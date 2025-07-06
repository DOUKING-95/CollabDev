package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>  {
}
