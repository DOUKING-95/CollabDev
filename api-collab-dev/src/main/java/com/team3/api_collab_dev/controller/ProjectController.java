package com.team3.api_collab_dev.controller;


import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "projects")
public class ProjectController {

    private ProjectService projectService;

    public void filtreProject (Project project, Level level) {

    }
}
