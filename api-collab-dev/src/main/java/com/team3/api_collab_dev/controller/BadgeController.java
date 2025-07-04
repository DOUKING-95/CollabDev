package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.entity.Badge;
import com.team3.api_collab_dev.service.BadgeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "badges")
public class BadgeController {

    private BadgeService badgeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED )
    public ResponseEntity<ApiReponse<?>> createBadge(@RequestBody Badge badge){
        return  ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.CREATED.value()),
                        HttpStatus.CREATED.getReasonPhrase(),
                        this.badgeService.createBadge(badge)
                )
        );
    }

}
