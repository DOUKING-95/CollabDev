package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.ManagerInfo;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.repository.ManagerInfoRepository;

import com.team3.api_collab_dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ManagerInfoService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ManagerInfoRepository managerInfoRepository;

    @Autowired
    private UserRepository userRepository;

    public ManagerInfo createManagerInfo(Long managerId, String githubLink, MultipartFile cvFile)
            throws IOException {

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager with ID " + managerId + " not found"));

        // Vérification supplémentaire du rôle si nécessaire
        if (!manager.getRole().getLabel().equals("MANAGER")) {
            throw new RuntimeException("User with ID " + managerId + " is not a manager");
        }

        // Reste du code pour le traitement du fichier...
        Path filePath = Paths.get(uploadDir, generateUniqueFileName(cvFile));
        Files.write(filePath, cvFile.getBytes());


        ManagerInfo managerInfo = new ManagerInfo();
        managerInfo.setManager(manager);
        managerInfo.setGithubLink(githubLink);
        managerInfo.setPathCv(filePath.toString());

        return managerInfoRepository.save(managerInfo);
    }

    private String generateUniqueFileName(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }

    public ManagerInfo getManagerInfo(Long id) {
        return managerInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager info not found"));
    }
}