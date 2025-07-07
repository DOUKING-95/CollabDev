package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.entity.ManagerInfo;
import com.team3.api_collab_dev.service.ManagerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/manager-info")
public class ManagerInfoController {

    @Autowired
    private ManagerInfoService managerInfoService;

    @PostMapping
    public ResponseEntity<?> createManagerInfo(
            @RequestParam("managerId") Long managerId,
            @RequestParam("githubLink") String githubLink,
            @RequestParam("cvFile") MultipartFile cvFile) {

        try {
            ManagerInfo savedInfo = managerInfoService.createManagerInfo(managerId, githubLink, cvFile);
            return ResponseEntity.ok(savedInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "status", "error",
                            "message", e.getMessage(),
                            "timestamp", LocalDateTime.now()
                    ));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "status", "error",
                            "message", "File upload failed",
                            "timestamp", LocalDateTime.now()
                    ));
        }
    }
}