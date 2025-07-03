package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtatProjetRepo extends JpaRepository<Project,Long> {
}
