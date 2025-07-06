package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.entity.Badge;
import com.team3.api_collab_dev.service.BadgeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "badges")
public class BadgeController {

    private BadgeService badgeService;

    @PostMapping("/assign")
    public ResponseEntity<String> assignBadge(
            @RequestParam long userId,
            @RequestParam long badgeId) {

        try {
            badgeService.assignedBadge(userId, badgeId);
            return ResponseEntity.ok("Badge attribué avec succès !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
