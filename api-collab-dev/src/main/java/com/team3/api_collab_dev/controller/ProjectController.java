package com.team3.api_collab_dev.controller;



import com.team3.api_collab_dev.dto.ProjectDTO;

import com.team3.api_collab_dev.entity.Project;

import com.team3.api_collab_dev.service.ProjectService;

import lombok.AllArgsConstructor;
import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.dto.ProjectDto;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.mapper.ProjectMapper;
import com.team3.api_collab_dev.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.Map;
import com.team3.api_collab_dev.service.RecommendationService;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.dto.UserRecommendationDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;





   



@AllArgsConstructor
@RestController
@RequestMapping(path = "projects")
public class ProjectController {

    private ProjectService projectService;
    private ProjectMapper projectMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse<?>> getAllProject(){

        return  ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(  new ApiReponse<>(
                      String.valueOf(  HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.projectService.getAllProjects()

                )
        );
    }
  
   @GetMapping("/{projectId}/pending-members")
    //Methode qui prend l'id et renvoie une type de donnée special sous forme de dictionnaire
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

    @PostMapping
    public  ResponseEntity<ApiReponse<?>> createProject(@RequestBody ProjectDto projectDto){
        Project project = projectMapper.apply(projectDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(

                new ApiReponse<>(
                        String.valueOf(HttpStatus.CREATED.value()),
                        HttpStatus.CREATED.getReasonPhrase(),
                        this.projectService.saveProject(project)
                )
        );
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<ApiReponse<?>>  getProjectById(@PathVariable(name = "userId") Long projectId){
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.projectService.findProjectById(projectId)
                )
        );

    }
}
