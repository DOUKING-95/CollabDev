package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepo extends CrudRepository<Project, Long> {



}
