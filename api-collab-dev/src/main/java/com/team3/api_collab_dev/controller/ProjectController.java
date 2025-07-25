package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.dto.ConfigureProjectDto;
import com.team3.api_collab_dev.dto.ProjectDto;
import com.team3.api_collab_dev.entity.Comment;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.mapper.ProjectMapper;
import com.team3.api_collab_dev.service.CommentService;
import com.team3.api_collab_dev.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@RestController
@RequestMapping(path = "projects")
@Tag(name = "Project", description = "Manage project ")
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
    public ResponseEntity<ApiReponse<?>> createProject(@RequestBody @Valid ProjectDto projectDto) {
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
    // configuration du projet
    @PutMapping("/{id}/configureProject")
    public ProjectDto updateProject(@PathVariable Long id,
                                 @Valid @RequestBody ConfigureProjectDto project,
                                 @RequestParam Long managerProfilId) {
        return projectService.updateProject(id, project, managerProfilId);
    }



    @PostMapping("/{id}/join")
    public Project addToPendingProfiles(@PathVariable Long id,
                                        @RequestBody Long profileId) {
        return projectService.addToPendingProfiles(id, profileId);
    }



    @PutMapping(path = "/{projectId}")
    public ResponseEntity<ApiReponse<?>> makeComment(@PathVariable(name = "projectId") Long projectId, @RequestBody Comment comment) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.commentService.makeComment(projectId, comment))

        );


    }

    @GetMapping(path = "/{projectId}/pendingProfil")
    public ResponseEntity<ApiReponse<?>> getPendingProfil(@PathVariable(name = "projectId") Long projectId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.projectService.getAllPendingProfil(projectId).stream().filter(profil -> Objects.equals(profil.getProfilName().toString(), "DESIGNER")))

        );


    }



}


