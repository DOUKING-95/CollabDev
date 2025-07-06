package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.Badge;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.repository.BadgeRepo;
import com.team3.api_collab_dev.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BadgeService {

    private BadgeRepo badgeRepo;
    private UserRepository userRepository;
    public void assignedBadge(long userId , long badgeId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouver"));
        Badge badge = badgeRepo.findById(badgeId)
                .orElseThrow(() -> new IllegalArgumentException("Badge non trouver"));
        user.setCurrentBadge(badge);
        userRepository.save(user);
    }
}
