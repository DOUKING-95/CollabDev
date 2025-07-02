package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.repository.ProjectRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectService {

    private ProjectRepo projectRepo;
}
