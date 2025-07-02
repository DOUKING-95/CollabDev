package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.Level;
import lombok.Data;

import java.util.List;

// DTO pour la réponse
@Data
public class UserRecommendationDTO {
    private Long id;
    private User user;
    private Level level;
    private double coins;
    private List<Task> tasks;
    private Profil profil;

    public UserRecommendationDTO(Long id, User user, Level level, double coins, List<Task> tasks, Profil profil) {
        this.id = id;
        this.user = user;
        this.level = level;
        this.coins = coins;
        this.tasks = tasks;
        this.profil = profil;
    }
}
