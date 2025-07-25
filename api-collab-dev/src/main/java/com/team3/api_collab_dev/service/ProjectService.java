package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.dto.ConfigureProjectDto;
import com.team3.api_collab_dev.dto.FilterProjectResponse;
import com.team3.api_collab_dev.dto.ProjectDto;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.mapper.FilterProjectMapper;
import com.team3.api_collab_dev.mapper.ProjectMapper;
import com.team3.api_collab_dev.mapper.UserMapper;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.team3.api_collab_dev.enumType.Level.*;


@AllArgsConstructor
@Service

public class ProjectService {

    private ProjectRepo projectRepo;
    private ProjectMapper projectMapper;
    private UserRepo userRepo;
    private ProfilRepo profilRepo;
    private FilterProjectMapper filterProjectMapper;
    private UserMapper userMapper;


    public List<FilterProjectResponse> filterProjectsByLevel(Level level) {
        // Étape 1 : Récupérer tous les projets
        List<Project> projects = (List<Project>) projectRepo.findAll();

        // Étape 2 : Filtrer par niveau
        List<Project> filteredProjects = projects.stream()
                .filter(project -> project.getLevel() == level).toList();

        // Étape 3 : Retourner la liste filtrée
        return filteredProjects.stream().map(project -> filterProjectMapper.apply(project) ).toList();
    }

    public Project saveProject(ProjectDto projectDto) {

        User author = userRepo.findById(projectDto.author().getId())
                .orElseThrow(() -> new EntityNotFoundException("Auteur introuvable avec l'id " + projectDto.author().getId()));

        Project project = projectMapper.apply(projectDto);
        project.setAuthor(author); // Remplacer l'auteur DTO par l'auteur récupéré depuis la base

        return projectRepo.save(project);
    }




    public List<ProjectDto> getAllProjects(){
        List<Project> projects = new ArrayList<>();

        this.projectRepo.findAll().forEach(projects::add);

        return  projects.stream().map((project) ->userMapper.projectToDto(project)).toList();
    }

    public ProjectDto findProjectById(Long projectId) {
        Project project = projectRepo.findById(projectId).
                orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID : "+projectId));

        return  userMapper.projectToDto(project);
    }

    public ProjectDto updateProject(Long id, ConfigureProjectDto updatedProject, Long managerProfilId){

        Profil profil = profilRepo.findById(managerProfilId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec le profil d'Id : " + managerProfilId));




        //Vérifier si le projet existe
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projet no trouvé avec l'ID : "+id));

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


        return userMapper.projectToDto(projectRepo.save(project));
    }

    public Project addToPendingProfiles(Long projectId, Long profileId){
        Project project = projectRepo.findById(projectId).
                orElseThrow(() -> new EntityNotFoundException("Projet non trouver avec l'ID : "+projectId));
        Profil profil = profilRepo.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("Profil on trouvé avec l'Id : "+profileId));
        project.getPendingProfiles().add(profil);
        return projectRepo.save(project);
    }

    private int attributeCoinsByLevel(Level level) {
        if (level.equals(BEGINNER)) {
            return 20;
        } else if (level.equals(INTERMEDIATE)) {
            return 30;
        }
     else if (level.equals(FREE)) {
        return 10;
    }
        else if (level.equals(ADVANCED)) {
            return 45;
        }
        throw new IllegalArgumentException("Niveau inconnu");
    }
    public  List<Profil> getAllPendingProfil(Long projectId){
        Project project = projectRepo.findById(projectId).orElseThrow( ()-> new  EntityNotFoundException("Pas de project trouver avec cette id" + projectId));

        return  project.getPendingProfiles();
    }


}
