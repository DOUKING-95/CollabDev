package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.repository.ProjectRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class ProjectService {

    private ProjectRepo projectRepo;

    public Project saveProject(Project project){
        return  this.projectRepo.save(project);

    }

    public List<Project> getAllProjects(){
        List<Project> projects = new ArrayList<>();

        this.projectRepo.findAll().forEach(projects::add);

        return  projects;
    }

    public Project findProjectById(Long projectId) {
        return projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Pas de projet avec cet ID : " + projectId));

    }

}
