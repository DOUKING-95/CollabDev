package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.repository.ConfigureProjectRepo;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.TaskRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.team3.api_collab_dev.enumType.Level.*;

@Service
public class ConfigureProjectService {

    @Autowired
    private ConfigureProjectRepo configureProjectRepo;

    @Autowired
    private ProfilRepo profilRepo;

    @Autowired
    private TaskRepo taskRepo;

    public Project updateProject(Long id, Project updatedProject, List<Long> selectedProfileIds){
        //Vérifier si le projet existe
        Project project = configureProjectRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projet no trouvé avec l'ID : "+id));

        //Mettre à jour les champs
        project.setLevel(updatedProject.getLevel());
        project.setSpecification(updatedProject.getSpecification());
        project.setGithubLink(updatedProject.getGithubLink());
        project.setStatus(updatedProject.getStatus());
        project.setTasks(updatedProject.getTasks());
        //Calculer le nombre de coins
        project.setCoins(calculateCoins(project.getLevel()));

        //Ajouter les profils sélectionnés comme membres
        if(selectedProfileIds != null ){
            List<Profil> selectedProfiles = (List<Profil>) profilRepo.findAllById(selectedProfileIds);
            project.getMembers().addAll(selectedProfiles);
            project.getPendingProfiles().removeAll(selectedProfiles);
        }
        return configureProjectRepo.save(project);
    }

    //Ajouter un profil à la liste d'attente
    public Project addToPendingProfiles(Long projectId, Long profileId){
        Project project = configureProjectRepo.findById(projectId).
                orElseThrow(() -> new EntityNotFoundException("Projet non trouver avec l'ID : "+projectId));
        Profil profil = profilRepo.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("Profil on trouvé avec l'Id : "+profileId));
        project.getPendingProfiles().add(profil);
        return configureProjectRepo.save(project);
    }

    //Récupérer un projet par son ID
    public Project getProject(Long id){
        return configureProjectRepo.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID : "+id));
    }

    private int calculateCoins(Level level) {
        if (level.equals(BEGINNER)) {
            return 5;
        } else if (level.equals(INTERMEDIATE)) {
            return 10;
        } else if (level.equals(ADVANCED)) {
            return 15;
        }
        throw new IllegalArgumentException("Niveau inconnu");
    }
}