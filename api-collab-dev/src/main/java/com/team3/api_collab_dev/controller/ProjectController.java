package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.*;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.service.CommentService;
import com.team3.api_collab_dev.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping(path = "projects")
@Tag(name = "Project", description = "Manage project ")
public class ProjectController {

    private ProjectService projectService;
    private CommentService commentService;




    @GetMapping(path ="/getByStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse<?>> getProjectByStatus(
            @RequestParam Status status
            ) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ApiReponse<>(
                                String.valueOf(HttpStatus.ACCEPTED.value()),
                                HttpStatus.ACCEPTED.getReasonPhrase(),
                                this.projectService.getDoneProject(status)

                        )
                );
    }

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
        return ResponseEntity.status(HttpStatus.CREATED).body(

                new ApiReponse<>(
                        String.valueOf(HttpStatus.CREATED.value()),
                        HttpStatus.CREATED.getReasonPhrase(),
                        this.projectService.saveProject(projectDto)
                )
        );
    }

    @GetMapping(path = "/{projectId}")
    public ResponseEntity<ApiReponse<?>> getProjectById(@PathVariable(name = "projectId") Long projectId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.projectService.findProjectById(projectId)
                )
        );
    }



    @GetMapping(path = "/{userId}/projectsUserDevelopper")
    public ResponseEntity<ApiReponse<?>> getProjectsByUserAsDevelopper(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.projectService.getProjectsByUserAsDevelopper(userId)
                )
        );
    }

    @GetMapping(path = "/{userId}/projectsUserDesigner")
    public ResponseEntity<ApiReponse<?>> getProjectsByUserAsDesigner(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.projectService.getProjectsByUserAsDesigner(userId)
                )
        );
    }

    @GetMapping(path = "/{userId}/projectsUserManager")
    public ResponseEntity<ApiReponse<?>> getProjectsByUserAsManager(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.projectService.getProjectsByUserAsManager(userId)
                )
        );
    }

    @GetMapping(path = "/{userId}/projectsUserAll")
    public ResponseEntity<ApiReponse<?>> getAllProjectsByUser(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.projectService.getAllProjectsByUser(userId)
                )
        );
    }

    // configuration du projet
    @PutMapping("/{id}/configureProject")
    public ProjectDto updateProject(
            @PathVariable Long id,
           // @Valid @RequestBody ConfigureProjectDto project,
          @Valid @ModelAttribute ConfigureProjectDto project,
            @RequestParam Long managerProfilId,
            @RequestPart(value = "file", required = false) MultipartFile file
            ) throws IOException {
        return projectService.updateProject(id, project, managerProfilId,file);
    }


    @PostMapping("/{id}/join")
    public Project addToPendingProfiles(
            @PathVariable Long id,
            @RequestBody Long profileId) {
        return projectService.addToPendingProfiles(id, profileId);
    }

    //TODO: pas de pathVariables des cette fonctions
    @PutMapping(path = "/{projectId}/makeComment")
    public ResponseEntity<ApiReponse<?>> makeComment(
            @PathVariable(name = "projectId") Long projectId,
            @PathVariable Long userId,
            @RequestBody @Valid CommentDto comment) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.commentService.makeComment(projectId,userId,  comment))

        );


    }

    @GetMapping(path = "/{projectId}/pendingDesigners")
    public ResponseEntity<ApiReponse<?>> getPendingProfil(@PathVariable(name = "projectId") Long projectId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.projectService.getAllPendingProfil(projectId).stream()
                                .filter(profil -> Objects.equals(profil.getProfilName()
                                        .toString(), "DESIGNER")))

        );


    }

    @GetMapping(path = "/{userId}/userAllpendingProjects")
    public ResponseEntity<ApiReponse<?>> getProjectsWithPendingRequests(
            @PathVariable(name = "userId") Long userId) {

        List<ProjectDto> projects = this.projectService.getProjectsWithUserPendingRequests(userId);
        String message = String.format("%d projet(s) trouvé(s) avec des demandes en attente", projects.size());

        System.out.println(message); // Affiche dans les logs du serveur

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        message, // Message dynamique avec le count
                        projects)
        );
    }

    @DeleteMapping(path = "/{projectId}/removeUsertoPending/{userId}")
    public ResponseEntity<ApiReponse<?>> removePendingRequest(
            @PathVariable(name = "projectId") Long projectId,
            @PathVariable(name = "userId") Long userId) {

        String message = this.projectService.removePendingRequestByUser(projectId, userId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        message)
        );
    }

    @GetMapping(path = "/withManagerRequests")
    public ResponseEntity<ApiReponse<?>> getProjectsWithManagerPendingRequests() {
        List<ProjectDto> projects = this.projectService.getProjectsWithManagerPendingRequests();
        String message = String.format("%d projet(s) trouvé(s) avec des demandes MANAGER en attente", projects.size());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        message,
                        projects)
        );
    }

    // Récupérer tous les managers en attente pour un projet
    @GetMapping(path = "/{projectId}/pendingManagers")
    public ResponseEntity<ApiReponse<?>> getManagerPendingProfilesForProject(
            @PathVariable(name = "projectId") Long projectId) {
        List<ProfilDto> pendingManagers = this.projectService.getManagerPendingProfilesForProject(projectId);
        String message = String.format("%d demande(s) MANAGER en attente pour ce projet", pendingManagers.size());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        message,
                        pendingManagers)
        );
    }



    // Récupérer tous les contributeurs en attente pour un projet
    @GetMapping(path = "/{projectId}/pendingContributors")
    public ResponseEntity<ApiReponse<?>> getPendingContributorsForProject(
            @PathVariable(name = "projectId") Long projectId) {

        List<ProfilDto> pendingContributors = this.projectService.getPendingContributors(projectId);
        String message = String.format("%d contributeur(s) en attente pour ce projet", pendingContributors.size());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        message,
                        pendingContributors)
        );
    }




}