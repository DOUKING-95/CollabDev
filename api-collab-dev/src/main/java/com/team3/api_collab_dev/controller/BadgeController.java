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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Badge> createBadge(@RequestBody Badge badge){
        return  ResponseEntity.ok(badgeService.createBadge(badge));
    }

}
