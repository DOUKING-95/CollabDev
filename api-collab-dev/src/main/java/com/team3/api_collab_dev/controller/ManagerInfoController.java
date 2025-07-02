package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.entity.ManagerInfo;
import com.team3.api_collab_dev.service.ManagerInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "managerInfo")
public class ManagerInfoController {

    private ManagerInfoService managerInfoService;

    public  ResponseEntity<ApiReponse> getAllManagerInfo(){
        List<ManagerInfo> managerInfos = new ArrayList<>();

        this.managerInfoService.getAllManagerInfos().forEach(managerInfos :: add);
         return  ResponseEntity.status(HttpStatus.ACCEPTED).body( new ApiReponse(
                 String.valueOf(HttpStatus.ACCEPTED.value()),
                 HttpStatus.ACCEPTED.getReasonPhrase(),
                 managerInfos
         ));

    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse> createManagerInfo(@RequestBody ManagerInfo managerInfo){
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body( new ApiReponse(
               String.valueOf( HttpStatus.ACCEPTED.value()),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                this.managerInfoService.saveManager(managerInfo)
        ));
    }
}
