package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.ManagerInfo;
import com.team3.api_collab_dev.repository.ManagerInfoRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManagerInfoService {

    private ManagerInfoRepo managerInfoRepo;


    public  Iterable<ManagerInfo> getAllManagerInfos(){
        return  this.managerInfoRepo.findAll();
    }

    public ManagerInfo saveManager(ManagerInfo managerInfo){
        return  this.managerInfoRepo.save(managerInfo);
    }
}
