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
        // Étape 1 : récupérer le projet
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé"));

        // Étape 2 : vérifier que le projet est validé
        if (project.getStatus() != Status.VALIDATED) {
            throw new IllegalStateException("Le projet n'est pas encore validé");
        }

        // Étape 3 : récupérer le manager
        User manager = project.getManager();
        if (manager == null) {
            throw new IllegalStateException("Ce projet n'a pas de manager attribué");
        }

        // Étape 4 : récupérer le profil du manager (en supposant qu'il n'a qu'un profil de type "MANAGER")
        Profil managerProfil = profilRepo.findByUserIdAndProfilType(manager.getId(), ProfilType.MANAGER.toString())
                .orElseThrow(() -> new EntityNotFoundException("Le manager n'a pas de profil"));

        // Étape 5 : ajouter les coins
        managerProfil.setCoins(managerProfil.getCoins() + coins);

        // Étape 6 : sauvegarder le profil
        profilRepo.save(managerProfil);

        return "Des pièces ont été attribuées au manager " + manager.getPseudo();
    }






}
