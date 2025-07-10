/*package com.team3.api_collab_dev.service;

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

}*/
























package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.ManagerInfo;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.RoleType;
import com.team3.api_collab_dev.enumType.Status; // Ajout de l'import
import com.team3.api_collab_dev.enumType.ValidationType; // Ajout de l'import
import com.team3.api_collab_dev.repository.ManagerInfoRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.TaskRepo;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ManagerInfoService {

    private ManagerInfoRepo managerInfoRepo;
    private UserRepo userRepo;
    private ProjectRepo projectRepo;
    private ProfilRepo profilRepo;
    private TaskRepo taskRepo;

    // F. Récupérer toutes les informations des managers
    public Iterable<ManagerInfo> getAllManagerInfos() {
        return this.managerInfoRepo.findAll();
    }

    // G. Enregistrer une nouvelle information de manager
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

    // H. Sélectionner un profil et l'ajouter à un projet
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

    // I. Attribuer une tâche à un profil (designer ou développeur) par un manager
    @Transactional
    public Task assignTask(Long managerId, Long projectId, Long profilId, Task task) {
        // J. Valider l'existence du manager
        ManagerInfo managerInfo = managerInfoRepo.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Manager non trouvé avec l'ID : " + managerId));
        User manager = managerInfo.getManager();

        // K. Vérifier si l'utilisateur est un manager
        if (!RoleType.MANAGER.equals(manager.getRole())) {
            throw new IllegalArgumentException("L'utilisateur avec l'ID " + managerId + " n'est pas un manager");
        }

        // L. Valider l'existence du projet
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID : " + projectId));

        // M. Valider l'existence du profil et son éligibilité
        Profil profil = profilRepo.findById(profilId)
                .orElseThrow(() -> new EntityNotFoundException("Profil non trouvé avec l'ID : " + profilId));

        // N. Vérifier si le profil est membre du projet
        if (!project.getMembers().contains(profil)) {
            throw new IllegalArgumentException("Le profil avec l'ID " + profilId + " n'est pas membre du projet " + projectId);
        }

        // O. Configurer les détails de la tâche
        task.setProject(project);
        task.setProfil(profil); // Utilisation de 'profil' au lieu de 'assignedTo' selon l'entité Task
        task.setTaskName(task.getTaskName() != null ? task.getTaskName() : "Tâche par défaut");
        task.setStatus(Status.PENDING); // Statut par défaut
        task.setIsValid(ValidationType.PENDING); // Validation par défaut

        // P. Enregistrer la tâche dans la base de données
        Task savedTask = taskRepo.save(task);

        // Q. Mettre à jour les relations (géré par cascade si configuré)
        project.getTasks().add(savedTask);
        profil.getTasks().add(savedTask);

        // R. Enregistrer les entités mises à jour (optionnel si cascade est suffisant)
        projectRepo.save(project);
        profilRepo.save(profil);

        // S. Retourner la tâche assignée
        return savedTask;
    }
}
