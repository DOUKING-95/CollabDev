package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.dto.AssignTasksDTO;
import com.team3.api_collab_dev.dto.CreateTasksDTO;
import com.team3.api_collab_dev.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/create-Tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse<?>> createTasks(
            @RequestBody CreateTasksDTO tasksDTO, // Reçoit les données JSON des tâches
            @RequestParam("xUserId") Long userId) { // ID de l'utilisateur connecté (simulé)
        // Vérifie si les données sont valides
        if (tasksDTO == null || tasksDTO.projectId() == null || tasksDTO.tasks() == null || tasksDTO.tasks().isEmpty()) {
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
            String result = taskService.createTasks(userId, tasksDTO); // Appelle le service
            Map<String, Object> response = new HashMap<>();
            response.put("code", String.valueOf(HttpStatus.CREATED.value()));
            response.put("message", HttpStatus.CREATED.getReasonPhrase());
            response.put("data", result);
            return ResponseEntity.status(HttpStatus.CREATED).body(
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
                            String.valueOf(HttpStatus.ACCEPTED.value()),
                            HttpStatus.ACCEPTED.getReasonPhrase(),
                            response
                    )
            );
        }
    }

    @PostMapping(value = "/assignTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse<?>> assignTasksToProfil(
            @RequestBody AssignTasksDTO assignDTO, // Reçoit les données JSON pour l'attribution
            @RequestParam("xUserId") Long userId) { // ID de l'utilisateur connecté (simulé)
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
            String result = taskService.assignTasksToProfil(userId, assignDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("code", String.valueOf(HttpStatus.OK.value()));
            response.put("message", HttpStatus.OK.getReasonPhrase());
            response.put("data", result);
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

    @PutMapping("/submitTask/{taskId}")
    public ResponseEntity<ApiReponse<?>> submitTask(
            @PathVariable Long taskId,
            @RequestParam("xUserId") Long contributorId) {

        Map<String, Object> response = new HashMap<>();
        try {
            String result = taskService.submitTask(taskId, contributorId);
            response.put("code", String.valueOf(HttpStatus.OK.value()));
            response.put("message", result);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    new ApiReponse<>(
                            String.valueOf(HttpStatus.ACCEPTED.value()),
                            HttpStatus.ACCEPTED.getReasonPhrase(),
                            response
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiReponse<>(
                            String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            HttpStatus.ACCEPTED.getReasonPhrase(),
                            response
                    )
            );
        }
    }
}