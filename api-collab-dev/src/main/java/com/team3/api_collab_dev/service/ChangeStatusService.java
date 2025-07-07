package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.repository.ChangeStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeStatusService {

    private final ChangeStatusRepository changeStatusRepository;

    public void soumettreProjetPourValidation(Long projetId) {
        Project projet = changeStatusRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));
        projet.setStatus(Status.EN_COURS_DE_VALIDATION);
        changeStatusRepository.save(projet);
    }


    public void validerProjet(Long projetId, String decision) {
        Project projet = changeStatusRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));

        if (projet.getStatus() != Status.EN_COURS_DE_VALIDATION) {
            throw new IllegalStateException("Ce projet n’est pas en attente de validation.");
        }

        // Nettoyage de la décision
        String cleanedDecision = decision.trim().toUpperCase();

        switch (cleanedDecision) {
            case "ACCEPTE":
                projet.setStatus(Status.ACCEPTE);
                break;
            case "REFUSE":
                projet.setStatus(Status.REFUSE);
                break;
            default:
                throw new IllegalArgumentException("Décision invalide : utilisez ACCEPTE ou REFUSE.");
        }

        changeStatusRepository.save(projet);
    }

    public List<Project> getProjetsParStatus(Long adminId, Status status) {
        return changeStatusRepository.findByGestionnaireIdAndStatus(adminId, status);
    }
}
