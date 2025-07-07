package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.dto.ProjectDto;
import com.team3.api_collab_dev.entity.Comment;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.mapper.ProjectMapper;
import com.team3.api_collab_dev.service.CommentService;
import com.team3.api_collab_dev.service.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping(path = "projects")
public class ProjectController {

    private ProjectService projectService;
    private ProjectMapper projectMapper;
    private CommentService commentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse<?>> getAllProject() {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ApiReponse<>(
                                String.valueOf(HttpStatus.ACCEPTED.value()),
                                HttpStatus.ACCEPTED.getReasonPhrase(),
                                this.projectService.getAllProjects()

                        )
                );
    }

    @PostMapping
    public ResponseEntity<ApiReponse<?>> createProject(@RequestBody ProjectDto projectDto) {
        Project project = projectMapper.apply(projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(

                new ApiReponse<>(
                        String.valueOf(HttpStatus.CREATED.value()),
                        HttpStatus.CREATED.getReasonPhrase(),
                        this.projectService.saveProject(projectDto)
                )
        );
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<ApiReponse<?>> getProjectById(@PathVariable(name = "userId") Long projectId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.projectService.findProjectById(projectId)
                )
        );
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id,
                                 @Valid @RequestBody Project project,
                                 @RequestParam(required = false) List<Long> selectedProfileIds) {
        return projectService.updateProject(id, project, selectedProfileIds);
    }

    //TODO: A Tester cette endpoint  fais sekou keita
    // Endpoint POST pour ajouter un profil à la liste d’attente
    @PostMapping("/{id}/join")
    public Project addToPendingProfiles(@PathVariable Long id,
                                        @RequestBody Long profileId) {
        return projectService.addToPendingProfiles(id, profileId);
    }


    //TODO: A Tester cette endpoint  fais sekou keita
    // Endpoint GET pour récupérer les détails d’un projet
    @GetMapping("/{id}")
    public Project getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }
//TODO : A Tester pour demain matin
    @PutMapping(path = "/{projectId}")
    public ResponseEntity<ApiReponse<?>> makeComment(@PathVariable(name = "projectId") Long projectId, @RequestBody Comment comment) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.commentService.makeComment(projectId, comment))

        );

    }
}
