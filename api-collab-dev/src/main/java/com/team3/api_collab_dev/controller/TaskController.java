package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.CreateTasksDTO;
import com.team3.api_collab_dev.service.TaskService;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/create-Tasks")
    public ResponseEntity<Map<String, Object>> createTasks(
            @RequestBody CreateTasksDTO tasksDTO, // Reçoit les données JSON des tâches
            @RequestHeader("X-User-Id") Long userId) { // ID de l'utilisateur connecté (simulé)
        // Vérifie si les données sont valides
        if (tasksDTO == null || tasksDTO.projectId() == null || tasksDTO.tasks() == null || tasksDTO.tasks().isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", "400");
            response.put("message", "Paramètres manquants ou invalides");
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            String result = taskService.createTasks(userId, tasksDTO); // Appelle le service
            Map<String, Object> response = new HashMap<>();
            response.put("code", String.valueOf(HttpStatus.CREATED.value()));
            response.put("message", HttpStatus.CREATED.getReasonPhrase());
            response.put("data", result);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", "500");
            response.put("message", "Erreur serveur");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/submitTask/{taskId}")
    public ResponseEntity<Map<String, Object>> submitTask(
            @PathVariable Long taskId,
            @RequestHeader("X-User-Id") Long contributorId) {

        Map<String, Object> response = new HashMap<>();
        try {
            String result = taskService.submitTask(taskId, contributorId);
            response.put("code", String.valueOf(HttpStatus.OK.value()));
            response.put("message", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", "400");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}