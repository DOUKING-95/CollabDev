package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.entity.ManagerInfo;
import com.team3.api_collab_dev.service.ManagerInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "managerInfo")
@Tag(name = "Mannager", description = "Manage Manager right section(controller)")
public class ManagerInfoController {

    private ManagerInfoService managerInfoService;

    public  ResponseEntity<ApiReponse<?>> getAllManagerInfo(){
        List<ManagerInfo> managerInfos = new ArrayList<>();

        this.managerInfoService.getAllManagerInfos().forEach(managerInfos :: add);
         return  ResponseEntity.status(HttpStatus.ACCEPTED).body( new ApiReponse<>(
                 String.valueOf(HttpStatus.ACCEPTED.value()),
                 HttpStatus.ACCEPTED.getReasonPhrase(),
                 managerInfos
         ));

    }

//TODO: A Tester cette end point demain
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse<?>> createManagerInfo(@RequestBody ManagerInfo managerInfo){
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body( new ApiReponse<>(
               String.valueOf( HttpStatus.ACCEPTED.value()),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                this.managerInfoService.saveManager(managerInfo)
        ));
    }
    //TODO: A Tester cette end point fais par moi
    @PutMapping(path = "/selectProfilAndAddToProject")
    public  ResponseEntity<ApiReponse<?>> selectProfilAndAddToProject (@RequestParam Long profilId, @RequestParam Long projectId){

        return  ResponseEntity.status(HttpStatus.ACCEPTED).body( new ApiReponse<>(
                String.valueOf( HttpStatus.ACCEPTED.value()),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                this.managerInfoService.selectProfilAndAddToProject(profilId, projectId)
        ));
    }

    @PostMapping("/assignCoins")
    public ResponseEntity<String> assignPoints(
            @RequestParam Long taskId,
            @RequestParam Long userId) {

        managerInfoService.assignPoints( taskId );
        return ResponseEntity.ok("Points attribués avec succès");
    }



}
