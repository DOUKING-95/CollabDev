package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.repository.ConfigureProjectRepo;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigureProjectService {

    @Autowired
    private ConfigureProjectRepo configureProjectRepo;

    @Autowired
    private ProfilRepo profilRepo;

    @Autowired
    private TaskRepo taskRepo;

}
