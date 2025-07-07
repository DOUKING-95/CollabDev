package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.dto.LivrableRequestDTO;
import com.team3.api_collab_dev.dto.LivrableResponseDTO;
import com.team3.api_collab_dev.entity.Livrable;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.repository.LivrableRepository;
import com.team3.api_collab_dev.repository.ProjetRepository;
import com.team3.api_collab_dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LivrableService {

    private final LivrableRepository livrableRepo;
    private final ProjetRepository projetRepo;
    private final UserRepository userRepo;

    public Livrable soumettreLivrable(LivrableRequestDTO dto) {
        User auteur = userRepo.findById(dto.getAuteurId())
                .orElseThrow(() -> new RuntimeException("Auteur introuvable"));
        Project projet = projetRepo.findById(dto.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet introuvable"));

        Livrable livrable = new Livrable();
        livrable.setDescription(dto.getDescription());
        livrable.setAuteur(auteur);
        livrable.setProjet(projet);
        return livrableRepo.save(livrable);
    }

    public List<LivrableResponseDTO> getLivrablesPourGestionnaire(Long gestionnaireId) {
        List<Livrable> livrables = livrableRepo.findByProjet_Gestionnaire_Id(gestionnaireId);

        return livrables.stream()
                .map(liv -> new LivrableResponseDTO(
                        liv.getId(),
                        liv.getDescription(),
                        liv.getAuteur().getSpeudo(),
                        liv.getProjet().getTitle()
                ))
                .toList();
    }

}
