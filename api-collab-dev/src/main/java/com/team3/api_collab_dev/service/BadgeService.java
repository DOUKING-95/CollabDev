package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.Badge;
import com.team3.api_collab_dev.repository.BadgeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BadgeService {

    private BadgeRepo badgeRepo;



    public Badge createBadge(Badge badge){
        return  badgeRepo.save(badge);
    }




}
