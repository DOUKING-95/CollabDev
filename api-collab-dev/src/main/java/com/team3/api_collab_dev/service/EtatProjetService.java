package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.repository.EtatProjetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtatProjetService {

    @Autowired
    private EtatProjetRepo projetRepo;


    // Passage à EN_COURS (par l'ADMIN après affectation)
    public Project changerEtatEnCours(Long projetId) {
        Project projet = projetRepo.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));

        if (projet.getStatus() != Status.PENDING) {
            throw new RuntimeException("Projet non en attente");
        }

        projet.setStatus(Status.IN_PROGRESS);
        return projetRepo.save(projet);
    }

    // Soumission à validation (par le gestionnaire)
    public Project soumettrePourValidation(Long projetId) {
        Project projet = projetRepo.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));

        if (projet.getStatus() != Status.IN_PROGRESS) {
            throw new RuntimeException("Projet non en cours");
        }

        projet.setStatus(Status.AWAITING_VALIDATION);
        return projetRepo.save(projet);
    }

    // Validation ou rejet (par l'ADMIN)
    public Project validerOuRejeter(Long projetId, boolean accepte) {
        Project projet = projetRepo.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));

        if (projet.getStatus() != Status.AWAITING_VALIDATION) {
            throw new RuntimeException("Projet non soumis à validation");
        }

        projet.setStatus(accepte ? Status.APPROVED : Status.REJECTED);
        return projetRepo.save(projet);
    }
}
