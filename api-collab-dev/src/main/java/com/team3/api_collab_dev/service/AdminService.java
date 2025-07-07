package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.*;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class AdminService {

    private UserRepo userRepo;
    private ProjectRepo projectRepo;
    private ProfilRepo profilRepo;


    public String attributeManagerCoins(Long projectId, double coins) {

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé"));


        if (project.getStatus() != Status.VALIDATED) {
            throw new IllegalStateException("Le projet n'est pas encore validé");
        }


        User manager = project.getManager();
        if (manager == null) {
            throw new IllegalStateException("Ce projet n'a pas de manager attribué");
        }


        Profil managerProfil = profilRepo.findByUserIdAndProfilName(manager.getId(), ProfilType.MANAGER.toString())
                .orElseThrow(() -> new EntityNotFoundException("Le manager n'a pas de profil"));

        managerProfil.setCoins(managerProfil.getCoins() + coins);

        profilRepo.save(managerProfil);

        return "Des pièces ont été attribuées au manager " + manager.getPseudo();
    }

    public String attributeManagerToProject(Long projectId, Long managerId) {

        // Étape 1 : récupérer le projet
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé"));
        User manager = userRepo.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Aucun manager trouver avec ce id" + managerId));

        if (project.getManager() != null) {

            project.setManager(manager);
            this.projectRepo.save(project);

        } else return "Le projet a déjà un Manager";


        return " :) Felicitation Mr/Mmme " + manager.getPseudo() + "vous une manager du projet " + project.getTitle();
    }


    public Boolean trustProject(Long projectId) {
        // Étape 1 : récupérer le projet
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé"));

        project.setStatus(Status.VALIDATED);
        this.projectRepo.save(project);

        return true;
    }


}
