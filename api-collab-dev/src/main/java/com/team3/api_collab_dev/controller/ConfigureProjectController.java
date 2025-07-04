package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.service.ConfigureProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "configureprojects")
public class ConfigureProjectController {

    @Autowired
    private ConfigureProjectService configureProjectService;

    // Endpoint PUT pour configurer un projet
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id,
                                 @Valid @RequestBody Project project,
                                 @RequestParam(required = false) List<Long> selectedProfileIds) {
        return configureProjectService.updateProject(id, project, selectedProfileIds);
    }

    // Endpoint POST pour ajouter un profil à la liste d’attente
    @PostMapping("/{id}/join")
    public Project addToPendingProfiles(@PathVariable Long id,
                                        @RequestBody  Long profileId) {
        return configureProjectService.addToPendingProfiles(id, profileId);
    }

    // Endpoint GET pour récupérer les détails d’un projet
    @GetMapping("/{id}")
    public Project getProject(@PathVariable Long id) {
        return configureProjectService.getProject(id);
    }
}
