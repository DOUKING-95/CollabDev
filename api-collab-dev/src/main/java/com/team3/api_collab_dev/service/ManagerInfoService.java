package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.ManagerInfo;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ManagerInfoService {

    private ManagerInfoRepo managerInfoRepo;
    private UserRepo userRepo;


    private ProjectRepo projectRepo;

    private ProfilRepo profilRepo;


    public  Iterable<ManagerInfo> getAllManagerInfos(){
        return  this.managerInfoRepo.findAll();
    }

    public ManagerInfo saveManager(ManagerInfo managerInfo) {

        Long managerId = managerInfo.getManager() != null ? managerInfo.getManager().getId() : null;
        if (managerId == null) {
            throw new IllegalArgumentException("L'ID du manager est requis");
        }

        User manager = userRepo.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Aucun manager trouvé avec l'ID : " + managerId));
        managerInfo.setManager(manager);

        return managerInfoRepo.save(managerInfo);
    }


    public String selectProfilAndAddToProject(Long profilId, Long projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'Id : " + projectId));

        Profil profil = profilRepo.findById(profilId)
                .orElseThrow(() -> new EntityNotFoundException("Profil non trouvé avec l'Id : " + profilId));

        if (project.getMembers().contains(profil)) {
            return String.format("Le profil %d est déjà membre du projet %d.", profilId, projectId);
        }

        project.getMembers().add(profil);
        project.getPendingProfiles().remove(profil);

        projectRepo.save(project);

        return String.format("Le profil %d a été ajouté au projet %d avec succès.", profilId, projectId);
    }

}
