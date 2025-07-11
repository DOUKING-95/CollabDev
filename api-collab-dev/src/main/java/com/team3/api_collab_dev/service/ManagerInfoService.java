package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.*;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ManagerInfoService {

    private ManagerInfoRepo managerInfoRepo;
    private UserRepo userRepo;
    private final UserRepo userRepository;
    private final TaskRepo taskRepo ;
    private final ProfilRepo profilRepo;
    private ProjectRepo projectRepo;




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
        profil.setCoins( profil.getCoins() - project.getCoins());
        project.getPendingProfiles().remove(profil);

        projectRepo.save(project);

        return String.format("Le profil %s a été ajouté au projet %s avec succès.", profil.getUser().getPseudo(), project.getTitle());
    }



    @Transactional
    public Profil assignPoints(Long taskId, Long userId) {
        Task task = taskRepo.findById(taskId).orElseThrow(
                () -> new IllegalArgumentException("Tâche non trouvée")
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Utilisateur non trouvé")
        );

        if (task.getStatus() != Status.VALIDATED) {
            throw new IllegalStateException("La tâche doit être approuvée pour attribuer des points");
        }

        Project project = task.getProject();

        Profil profil = task.getProfil();
        profil.setCoins(profil.getCoins() + project.getCoins());
        return profilRepo.save(profil);
    }


}
