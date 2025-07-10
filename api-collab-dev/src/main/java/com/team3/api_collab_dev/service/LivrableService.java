package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.dto.LivrableRequestDTO;
import com.team3.api_collab_dev.dto.LivrableResponseDTO;
import com.team3.api_collab_dev.entity.Livrable;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.StatusLivrable;
import com.team3.api_collab_dev.repository.LivrableRepository;
import com.team3.api_collab_dev.repository.ProjetRepository;
import com.team3.api_collab_dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LivrableService {

    private final LivrableRepository livrableRepo;
    private final ProjetRepository projetRepo;
    private final UserRepository userRepo;

    // Gestionnaire crée un livrable pour un contributeur => statut EN_COURS
    public LivrableResponseDTO creerLivrable(LivrableRequestDTO dto) {
        User auteur = userRepo.findById(dto.getAuteurId())
                .orElseThrow(() -> new RuntimeException("Auteur introuvable"));
        Project projet = projetRepo.findById(dto.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));

        Livrable livrable = new Livrable();
        livrable.setDescription(dto.getDescription());
        livrable.setAuteur(auteur);
        livrable.setProjet(projet);
        livrable.setStatut(StatusLivrable.EN_COURS);
        livrable.setDateLivraison(LocalDate.now());

        Livrable saved = livrableRepo.save(livrable);

        return new LivrableResponseDTO(
                saved.getId(),
                saved.getDescription(),
                saved.getAuteur().getSpeudo(),
                saved.getProjet().getTitle(),
                saved.getStatut().name()
        );
    }


    // Contributeur soumet le livrable => statut TERMINE
    public LivrableResponseDTO soumettreLivrable(Long id) {
        Livrable livrable = livrableRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Livrable introuvable"));

        livrable.setStatut(StatusLivrable.TERMINE);
        Livrable updated = livrableRepo.save(livrable);

        return new LivrableResponseDTO(
                updated.getId(),
                updated.getDescription(),
                updated.getAuteur().getSpeudo(),
                updated.getProjet().getTitle(),
                updated.getStatut().name()
        );
    }


    // Liste des livrables pour un gestionnaire
    public List<LivrableResponseDTO> getLivrablesPourGestionnaire(Long gestionnaireId) {
        List<Livrable> livrables = livrableRepo.findByProjet_Gestionnaire_Id(gestionnaireId);

        return livrables.stream().map(liv -> new LivrableResponseDTO(
                liv.getId(),
                liv.getDescription(),
                liv.getAuteur().getSpeudo(),       // récupère le nom de l'auteur
                liv.getProjet().getTitle(),        // titre du projet
                liv.getStatut().name()             // statut (EN_COURS, TERMINE)
        )).toList();
    }

}
