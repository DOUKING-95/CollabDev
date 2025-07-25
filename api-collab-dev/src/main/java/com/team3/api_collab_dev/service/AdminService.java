package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.*;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
@Slf4j
public class AdminService {


    private ProjectRepo projectRepo;
    private ProfilRepo profilRepo;


    public String attributeManagerCoins(Long projectId, double coins) {

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé"));

        if (project.getStatus() != Status.VALIDATED) {
            throw new IllegalStateException("Le projet n'est pas encore validé");
        }

        Profil profil = project.getManager();
        if (profil == null) {
            throw new IllegalStateException("Ce projet n'a pas de manager attribué");
        }

        profil.setCoins(profil.getCoins() + coins);
        profilRepo.save(profil);

        return "Des pièces ont été attribuées au manager " + profil.getUser().getPseudo();
    }

    public String attributeManagerToProject(
            Long projectId,
            Long managerProfilId) {

        // Étape 1 : récupérer le projet
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé"));

        Profil managerProfil = profilRepo.findById(managerProfilId)
                .orElseThrow(() -> new EntityNotFoundException("Aucun manager trouver avec avec le profil id" + managerProfilId));

        if (project.getManager() == null) {

            project.setManager(managerProfil);
            project.getPendingProfiles().remove(managerProfil);
            this.projectRepo.save(project);

        } else {
            log.info("++++++++++++++++++++++++++++++++++++++++" + project.getManager().getUser().getPseudo());
            return "Le projet a déjà un Manager";
        }

        return " :) Felicitation Mr/Mmme " + managerProfil.getUser().getPseudo() + " vous une manager du projet " + project.getTitle();
    }


    public Boolean trustProject(Long projectId) {
        // Étape 1 : récupérer le projet
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé"));

        Profil managerProfil = project.getManager();

        if (!(project.getStatus().equals(Status.DONE))) {
            throw new RuntimeException("le statut du projet doit être done");
        }
        project.setStatus(Status.VALIDATED);
        this.projectRepo.save(project);

        if (project.getLevel() == Level.FREE
                || project.getLevel() == Level.BEGINNER
                || project.getLevel() == Level.INTERMEDIATE
                || project.getLevel() == Level.ADVANCED) {
            managerProfil.setCoins(project.getCoins() * 2);
        }

        managerProfil.setValidatedProjects(managerProfil.getValidatedProjects() + 1);

        this.profilRepo.save(managerProfil);
        return true;
    }


}
