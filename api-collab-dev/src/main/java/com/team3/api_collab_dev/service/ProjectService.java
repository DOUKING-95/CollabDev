package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.repository.ProjectRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service

public class ProjectService {

    private ProjectRepo projectRepo;


    public List<Project> filterProjectsByLevel(Level level) {
        // Étape 1 : Récupérer tous les projets
        List<Project> projects = (List<Project>) projectRepo.findAll();

        // Étape 2 : Filtrer par niveau
        List<Project> filteredProjects = projects.stream()
                .filter(project -> project.getLevel() == level).toList();

        // Étape 3 : Retourner la liste filtrée
        return filteredProjects;
    }

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
