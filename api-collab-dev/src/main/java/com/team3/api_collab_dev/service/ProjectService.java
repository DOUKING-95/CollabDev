package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.dto.ProjectDTO;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.repository.ProjectRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {

    private ProjectRepo projectRepo;

    public List<ProjectDTO> filterProjectsByLevel(Level level) {
        // Étape 1 : Récupérer tous les projets
        List<Project> projects = (List<Project>) projectRepo.findAll();

        // Étape 2 : Filtrer par niveau
        List<ProjectDTO> filteredProjects = projects.stream()
                .filter(project -> project.getLevel() == level)
                .map(project -> new ProjectDTO(
                        project.getId(),
                        project.getTitle(),
                        project.getLevel()
                ))
                .collect(Collectors.toList());

        // Étape 3 : Retourner la liste filtrée
        return filteredProjects;
    }
}
