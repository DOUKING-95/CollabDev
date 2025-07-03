package com.team3.api_collab_dev.controller;


import com.team3.api_collab_dev.dto.ProjectDTO;
import com.team3.api_collab_dev.dto.UserRecommendationDTO;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.service.ProjectService;
import com.team3.api_collab_dev.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(path = "projects")
public class ProjectController {

    private ProjectService projectService;

    public void filtreProject (Project project, Level level) {

    }

    @GetMapping("/{projectId}/pending-members")
    public ResponseEntity<Map<String, List<UserRecommendationDTO>>> getPendingMembers(@PathVariable Long projectId) {
        try {
            // 1. Appel du service pour obtenir les recommandations
            Map<String, List<UserRecommendationDTO>> pendingMembers =
                    RecommendationService.recommendPendingMembers(projectId);

            // 2. Retourne la réponse avec statut HTTP 200 (OK)
            return ResponseEntity.ok(pendingMembers);

        } catch (IllegalArgumentException e) {
            // 3. Gestion d'erreur si le projet n'existe pas (statut HTTP 400)
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/filter-by-level")
    public ResponseEntity<List<ProjectDTO>> filterProjectsByLevel(@RequestParam("level") Level level) {
        try {
            List<ProjectDTO> filteredProjects = projectService.filterProjectsByLevel(level);
            return ResponseEntity.ok(filteredProjects);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
