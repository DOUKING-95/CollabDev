package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.dto.ConfigureProjectDto;
import com.team3.api_collab_dev.dto.FilterProjectResponse;
import com.team3.api_collab_dev.dto.ProjectDto;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.mapper.FilterProjectMapper;
import com.team3.api_collab_dev.mapper.ProjectMapper;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.team3.api_collab_dev.enumType.Level.*;


@AllArgsConstructor
@Service

public class ProjectService {

    private ProjectRepo projectRepo;
    private UserRepo userRepo;
    private ProfilRepo profilRepo;
    private FilterProjectMapper filterProjectMapper;

    public List<ProjectDto> getProjectsByUserAsManager(Long userId) {
        List<Project> allProjects = new ArrayList<>();
        this.projectRepo.findAll().forEach(allProjects::add);

        List<Project> managerProjects = allProjects.stream()
                .filter(p -> p.getManager() != null) // Vérifie si le manager existe
                .filter(p -> p.getManager().getId() != null && p.getManager().getId().equals(userId))
                .toList();
        return managerProjects.stream().map(ProjectMapper::toDto).toList();
    }


    public List<ProjectDto> getProjectsByUserAsDevelopper(Long userId) {
        List<Project> allProjects = new ArrayList<>();
        this.projectRepo.findAll().forEach(allProjects::add);

        List<Project> contributorProjects = allProjects.stream()
                .filter(p -> p.getMembers().stream()
                        .anyMatch(m -> m.getUser().getId().equals(userId) &&
                                ("DEVELOPER".equalsIgnoreCase(m.getProfilName().toString())
                                )
                        )
                )
                .toList();

        return  contributorProjects.stream().map(ProjectMapper::toDto).toList();
    }

    public List<ProjectDto> getProjectsByUserAsDesigner(Long userId) {
        List<Project> allProjects = new ArrayList<>();
        this.projectRepo.findAll().forEach(allProjects::add);

        List<Project> contributorProjects = allProjects.stream()
                .filter(p -> p.getMembers().stream()
                        .anyMatch(m -> m.getUser().getId().equals(userId) &&
                                (
                                        "DESIGNER".equalsIgnoreCase(m.getProfilName().toString()))
                        )
                )
                .toList();
        return  contributorProjects.stream().map(ProjectMapper::toDto).toList();
    }

    public List<ProjectDto> getAllProjectsByUser(Long userId) {
        List<ProjectDto> managerProjects = getProjectsByUserAsManager(userId);
        List<ProjectDto> developerProjects = getProjectsByUserAsDevelopper(userId);
        List<ProjectDto> designerProjects = getProjectsByUserAsDesigner(userId);

        // Fusionner et supprimer les doublons
        Set<ProjectDto> allProjects = new LinkedHashSet<>();
        allProjects.addAll(managerProjects);
        allProjects.addAll(developerProjects);
        allProjects.addAll(designerProjects);
        return new ArrayList<>(allProjects);
    }

    public List<FilterProjectResponse> filterProjectsByLevel(Level level) {
        //Récupérer tous les projets
        List<Project> projects = (List<Project>) projectRepo.findAll();

        //Filtrer par niveau
        List<Project> filteredProjects = projects.stream()
                .filter(project -> project.getLevel() == level).toList();

        //Retourner la liste filtrée
        return filteredProjects.stream().map(project -> filterProjectMapper.apply(project)).toList();
    }

    public ProjectDto saveProject(ProjectDto projectDto) {

        User author = userRepo.findById(projectDto.author().id())
                .orElseThrow(() -> new EntityNotFoundException("Auteur introuvable avec l'id " + projectDto.author()));

        Project project = ProjectMapper.toEntity (projectDto);
        project.setAuthor(author); // Remplacer l'auteur DTO par l'auteur récupéré depuis la base
        project.setStatus(Status.TODO);
        System.out.println("Description reçue : " + project.getDescription());

        projectRepo.save(project);
        return ProjectMapper.toDto (project);
    }


    public List<ProjectDto> getAllProjects() {
        List<Project> projects = new ArrayList<>();

        this.projectRepo.findAll().forEach(projects::add);

        return projects.stream().map(ProjectMapper::toDto).toList();
    }

    public ProjectDto findProjectById(Long projectId) {
        Project project = projectRepo.findById(projectId).
                orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID : " + projectId));

        return ProjectMapper.toDto(project);
    }

    public ProjectDto updateProject(Long id, ConfigureProjectDto updatedProject, Long managerProfilId) {

        Profil profil = profilRepo.findById(managerProfilId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec le profil d'Id : " + managerProfilId));


        //Vérifier si le projet existe
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projet no trouvé avec l'ID : " + id));

        if (!profil.equals(project.getManager())) {
            throw new SecurityException("Seuls le manager de ce projet  peut peut le configurer.");
        }

        //Mettre à jour les champs
        project.setLevel(updatedProject.level());
        project.setSpecification(updatedProject.specification());
        project.setGithubLink(updatedProject.githubLink());
        project.setStatus(Status.TODO);
        //Calculer le nombre de coins
        project.setCoins(attributeCoinsByLevel(project.getLevel()));


        return ProjectMapper.toDto(projectRepo.save(project));
    }

    public Project addToPendingProfiles(Long projectId, Long profileId) {
        Project project = projectRepo.findById(projectId).
                orElseThrow(() -> new EntityNotFoundException("Projet non trouver avec l'ID : " + projectId));
        Profil profil = profilRepo.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("Profil on trouvé avec l'Id : " + profileId));
        project.getPendingProfiles().add(profil);
        return projectRepo.save(project);
    }

    private int attributeCoinsByLevel(Level level) {

        if (level.equals(BEGINNER)) {
            return 20;
        } else if (level.equals(INTERMEDIATE)) {
            return 30;
        } else if (level.equals(FREE)) {
            return 10;
        } else if (level.equals(ADVANCED)) {
            return 45;
        }
        throw new IllegalArgumentException("Niveau inconnu");
    }

    public List<Profil> getAllPendingProfil(Long projectId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Pas de project trouver avec cette id" + projectId));

        return project.getPendingProfiles();
    }


}