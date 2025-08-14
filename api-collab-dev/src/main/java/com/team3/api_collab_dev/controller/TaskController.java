package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.dto.AssignTasksDTO;
import com.team3.api_collab_dev.dto.CreateTasksDTO;
import com.team3.api_collab_dev.dto.TaskDto;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.TaskRepo;
import com.team3.api_collab_dev.service.MailService;
import com.team3.api_collab_dev.service.TaskService;

import jakarta.persistence.EntityNotFoundException;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
@Tag(name = "`Task", description = "Manage Task ")
public class TaskController {

    private final TaskService taskService;
    private MailService mailService;
    private ProfilRepo profilRepo;
    private TaskRepo taskRepo;



    @PostMapping(value = "/create-Tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse<TaskDto>> createTasks(
            @RequestBody CreateTasksDTO tasksDTO,
            @RequestParam("managerId") Long managerId) {

        // 1️⃣ Vérification des paramètres
        if (tasksDTO == null
                || tasksDTO.projectId() == null
                || tasksDTO.task() == null
                || tasksDTO.task().taskName() == null
                || tasksDTO.task().taskName().isEmpty()) {

            return ResponseEntity.badRequest().body(
                    new ApiReponse<>(
                            "400",
                            "Paramètres manquants ou invalides",
                            null
                    )
            );
        }

        try {
            // 2️⃣ Création de la tâche via le service
            TaskDto createdTask = taskService.createTasks(managerId, tasksDTO);

            // 3️⃣ Retour HTTP 201 avec TaskDto
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiReponse<>(
                            String.valueOf(HttpStatus.CREATED.value()),
                            HttpStatus.CREATED.getReasonPhrase(),
                            createdTask
                    )
            );

        } catch (SecurityException se) {
            // Erreur de permission
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ApiReponse<>("403", se.getMessage(), null)
            );

        } catch (EntityNotFoundException enfe) {
            // Projet ou profil non trouvé
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiReponse<>("404", enfe.getMessage(), null)
            );

        } catch (IllegalArgumentException iae) {
            // Tâche invalide
            return ResponseEntity.badRequest().body(
                    new ApiReponse<>("400", iae.getMessage(), null)
            );

        } catch (Exception e) {
            // Autres erreurs serveur
            e.printStackTrace(); // utile pour debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiReponse<>("500", "Erreur serveur", null)
            );
        }
    }


    @PostMapping(value = "/assignTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse<?>> assignTasksToProfil(
            @RequestBody AssignTasksDTO assignDTO,
            @RequestParam("managerId") Long userId) {
        // Vérifie si les données sont valides
        if (assignDTO == null || assignDTO.projectId() == null || assignDTO.profilIdCible() == null || assignDTO.taskIds() == null || assignDTO.taskIds().isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", "400");
            response.put("message", "Paramètres manquants ou invalides");
            response.put("data", null);
            return ResponseEntity.badRequest().body(
                    new ApiReponse<>(
                            String.valueOf(HttpStatus.ACCEPTED.value()),
                            HttpStatus.ACCEPTED.getReasonPhrase(),
                            response
                    )
                    );
        }

        try {
            String result = taskService.assignTasksToProfil(assignDTO, userId );
            Map<String, Object> response = new HashMap<>();
            response.put("code", String.valueOf(HttpStatus.OK.value()));
            response.put("message", HttpStatus.OK.getReasonPhrase());
            response.put("data", result);

            Iterable<Task> assignedTask = this.taskRepo.findAllById(assignDTO.taskIds());
            List<String> taskTitles = new ArrayList<>();
            assignedTask.forEach((task -> taskTitles.add(task.getTaskName())));

            Profil targetProfil = this.profilRepo.findById(assignDTO.profilIdCible())
                    .orElseThrow(()-> new EntityNotFoundException("Profil non trouver avec id " + assignDTO.profilIdCible()));

            String email = targetProfil.getUser().getEmail();


            this.mailService.sendEmail(email, "CollabDev API", String.format("Une nouvelle tâche vous a été assigner %s", String.join("|", taskTitles)));
            return ResponseEntity.ok().body(
                    new ApiReponse<>(
                            String.valueOf(HttpStatus.ACCEPTED.value()),
                            HttpStatus.ACCEPTED.getReasonPhrase(),
                            response
                    )
            );
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", "500");
            response.put("message", "Erreur serveur");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiReponse<>(
                            String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                            HttpStatus.ACCEPTED.getReasonPhrase(),
                            response
                    )
            );
        }
    }

    @PutMapping("/{taskId}/submitTask")
    public ResponseEntity<ApiReponse<?>> submitTask(
            @PathVariable Long taskId,
            @RequestParam("contributorId") Long contributorId) {


        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        taskService.submitTask(taskId, contributorId)
                )
        );

    }
}
