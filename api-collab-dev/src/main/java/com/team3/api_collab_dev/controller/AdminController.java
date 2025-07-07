package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping(path = "admin")
public class AdminController {

    private AdminService adminService;



    @PostMapping(value = "/attributeCoinsToManager", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> attributeManagerCoins(@RequestParam Long projectId, @RequestBody  double coins){

         return  ResponseEntity.status(HttpStatus.ACCEPTED).body(this.adminService.attributeManagerCoins(projectId, coins));
    }

    @PutMapping(path = "attributeManagerToProject")
    public ResponseEntity<ApiReponse<?>> attributeManagerToProject(@RequestParam Long projectId, @RequestParam Long managerId){

        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.adminService.attributeManagerToProject(projectId, managerId)
                )
        );
    }

    @PutMapping(path = "trustProject")
    public  ResponseEntity<ApiReponse<?>> trustProject(@RequestParam Long projectId){
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.adminService.trustProject(projectId)
                )
        );
    }
}
