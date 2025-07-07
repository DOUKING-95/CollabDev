package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.dto.ProjectDto;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.mapper.ProjectMapper;
import com.team3.api_collab_dev.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
