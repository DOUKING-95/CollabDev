package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.ManagerInfo;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.repository.ManagerInfoRepo;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.TaskRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ManagerInfoService {

    private ManagerInfoRepo managerInfoRepo;

    private final TaskRepo taskRepo;
    private final ProfilRepo profilRepo;
    private ProjectRepo projectRepo;


    public Iterable<ManagerInfo> getAllManagerInfos() {
        return this.managerInfoRepo.findAll();
    }

    public ManagerInfo saveManager(ManagerInfo managerInfo) {

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

        profil.setCoins(profil.getCoins() - project.getCoins());
        project.getPendingProfiles().remove(profil);
        projectRepo.save(project);
        return String.format("Le profil %s a été ajouté au projet %s avec succès.", profil.getUser().getPseudo(), project.getTitle());

    }


    @Transactional
    public Profil validateTask(Long taskId, Long managerId) {
        Task task = taskRepo.findById(taskId).orElseThrow(
                () -> new IllegalArgumentException("Tâche non trouvée")
        );

        Profil profilManager = profilRepo.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Profil non trouvé pour le manager ID : " + managerId));

        if (task.getStatus() != Status.DONE) {
            throw new IllegalStateException("La tâche doit être terminer avant d'être valider");
        }

        Project project = task.getProject();

        Profil profil = task.getProfil();

        if (!profilManager.getProfilName().equals(ProfilType.MANAGER)) {
            throw new SecurityException("Seuls les managers peuvent attribuer des coins.");
        }
        if (!(profilManager == project.getManager())) {
            throw new SecurityException("Vous n'êtes pas manager de ce projet :) Vous ne pouvez pas assigner des coins");
        }

        task.setStatus(Status.VALIDATED);

        if (project.getLevel() == Level.BEGINNER) {
            profil.setCoins(profil.getCoins() + (project.getCoins() + 5));
            profil.setValidatedProjects(profil.getValidatedProjects() + 1);

            return profilRepo.save(profil);

        } else if (project.getLevel() == Level.FREE) {
            profil.setCoins(profil.getCoins() + project.getCoins());

            return profilRepo.save(profil);
        } else if (project.getLevel() == Level.INTERMEDIATE) {
            profil.setCoins(profil.getCoins() + (project.getCoins() + 15));
            profil.setValidatedProjects(profil.getValidatedProjects() + 1);

            return profilRepo.save(profil);

        } else {
            profil.setCoins(profil.getCoins() + (project.getCoins() + 35));
            profil.setValidatedProjects(profil.getValidatedProjects() + 1);

            return profilRepo.save(profil);
        }


    }

    public String validateProject(Long managerProfilId, Long projectId) {
        Profil profil = profilRepo.findById(managerProfilId)
                .orElseThrow(() -> new EntityNotFoundException(" Profil Manager non trouvé avec l'id " + managerProfilId));
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec cet id " + projectId));
        if (!(project.getManager().equals(profil))) {
            throw new RuntimeException("Vous n'êtes pas manager de ce projet");
        }
        for (Task task : project.getTasks()) {
            if (!(task.getStatus() == Status.VALIDATED)) {
                throw new RuntimeException("Toute les taches doivent etre valider");
            }
        }
        project.setStatus(Status.DONE);
        projectRepo.save(project);
        return "Le projet est terminer";


    }

}
