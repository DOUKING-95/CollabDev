package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.ProfilType;
import lombok.Data;

// DTO pour la réponse
@Data
public class UserRecommendationDTO {
    private Long id;
    private String pseudo;
    private Level level;
    private double coins;
    private int validatedProjects;
    private ProfilType profilName;

    public UserRecommendationDTO(Long id, String pseudo, Level level, double coins, int validatedProjects, ProfilType role) {
        this.id = id;
        this.pseudo = pseudo;
        this.level = level;
        this.coins = coins;
        this.validatedProjects = validatedProjects;
        this.profilName = role;
    }
}
