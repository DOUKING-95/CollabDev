package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.dto.UserRecommendationDTO;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    // Référence au repository pour accéder aux données des projets
    private static ProjectRepo projectRepo;

    // Injection des dépendances via le constructeur
    @Autowired
    public RecommendationService(ProjectRepo projectRepository) {
            this.projectRepo = projectRepository;
    }

    // Méthode principale qui retourne les membres recommandés pour un projet donné
    public static Map<String, List<UserRecommendationDTO>> recommendPendingMembers(Long projectId) {
        // 1. Récupération du projet depuis la base de données
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));

        // 2. Initialisation des listes pour stocker les résultats
        List<Profil> pendingProfiles = project.getPendingProfiles();
        List<UserRecommendationDTO> developers = new ArrayList<>();
        List<UserRecommendationDTO> designers = new ArrayList<>();

        // Parcours de tous les profils en attente
        for (Profil profile : pendingProfiles) {
            User user = profile.getUser();
            // Conversion du profil en DTO pour l'exposition via l'API
            UserRecommendationDTO dto = new UserRecommendationDTO(
                    user.getId(),
                    user.getPseudo(),
                    profile.getLevel(),
                    (int) profile.getCoins(),
                    profile.getValidatedProjects(),
                    profile.getProfilName()
            );

            // Répartition des profils selon leur type
            if (profile.getProfilName() == ProfilType.DEVELOPPER) {
                developers.add(dto);
            } else if (profile.getProfilName() == ProfilType.DESIGNER) {
                designers.add(dto);
            }
        }

        // 3. Tri des listes selon les critères définis
        List<UserRecommendationDTO> sortedDevelopers = sortByCriteria(developers);
        List<UserRecommendationDTO> sortedDesigners = sortByCriteria(designers);

        // 4. Construction du résultat final
        Map<String, List<UserRecommendationDTO>> result = new HashMap<>();
        result.put("developers", sortedDevelopers);
        result.put("designers", sortedDesigners);
        return result;
    }

    // Méthode utilitaire pour trier les profils selon plusieurs critères
    private static List<UserRecommendationDTO> sortByCriteria(List<UserRecommendationDTO> profiles) {
        return profiles.stream()
                .sorted((profile1, profile2) -> {
                    // 1er critère : niveau (ordre décroissant)
                    int levelComparison = Integer.compare(
                            profile2.getLevel().ordinal(),
                            profile1.getLevel().ordinal()
                    );
                    if (levelComparison != 0) return levelComparison;

                    // 2ème critère : nombre de coins (ordre décroissant)
                    int coinsComparison = Integer.compare(
                            (int) profile2.getCoins(),
                            (int) profile1.getCoins()
                    );
                    if (coinsComparison != 0) return coinsComparison;

                    // 3ème critère : projets validés (ordre décroissant)
                    return Integer.compare(
                            profile2.getValidatedProjects(),
                            profile1.getValidatedProjects()
                    );
                })
                .collect(Collectors.toList());
    }
}