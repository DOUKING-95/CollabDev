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

import java.util.*;


@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
@Tag(name = "`Task", description = "Manage Task ")
public class TaskController {

    private final TaskService taskService;
    private MailService mailService;
    private ProfilRepo profilRepo;
    private TaskRepo taskRepo;



    @PostMapping(value = "/{projectId}/create-task", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse<?>> createTask(
            @PathVariable Long projectId,
            @RequestBody CreateTasksDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.CREATED.value()),
                        HttpStatus.CREATED.getReasonPhrase(),
                        this.taskService.createTask(projectId, dto)
                )
        );
    }


    @PostMapping(value = "/assignTask/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse<?>> assignTasksToProfil(
            @RequestBody AssignTasksDTO assignDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.CREATED.value()),
                        HttpStatus.CREATED.getReasonPhrase(),
                        this.taskService.assignTasksToProfil(assignDTO, assignDTO.profilIdCible() )
                )
        );
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
