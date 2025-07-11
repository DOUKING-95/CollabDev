package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.service.ProjectService;
import com.team3.api_collab_dev.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "systems")
@AllArgsConstructor
public class SystemController {

    private RecommendationService recommendationService;
    private ProjectService projectService;

    @GetMapping(path = "/projectReconmendation")
    public ResponseEntity<ApiReponse<?>> projectReconmendation(
            @RequestParam Long projectId){



        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.recommendationService.recommendPendingMembers( projectId)
                )
        );
    }

    @GetMapping(path = "/filterProjectsByLevel")
    public ResponseEntity<ApiReponse<?>> filterProjectsByLevel(@RequestParam Level level) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.projectService.filterProjectsByLevel(level))

        );

    }



}
