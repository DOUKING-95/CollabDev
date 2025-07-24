package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.dto.AssignTasksDTO;
import com.team3.api_collab_dev.dto.CreateTasksDTO;
import com.team3.api_collab_dev.dto.TaskInputDTO;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.TaskRepo;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TaskService {

    private  TaskRepo taskRepo;
    private ProjectRepo projectRepo;
    private ProfilRepo profilRepo;



    public String createTasks(Long profilId, CreateTasksDTO tasksDTO) {
        // 1. Vérifie si l'utilisateur a un profil manager
        Profil profil = profilRepo.findById(profilId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec le profil d'Id : " + profilId));

        if (!profil.getProfilName().equals(ProfilType.MANAGER)) {
            throw new SecurityException("Seuls les managers peuvent créer des tâches.");
        }

        // 2. Valide le projet et l'association du profil
        Project project = projectRepo.findById(tasksDTO.projectId())
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'Id : " + tasksDTO.projectId()));

        if (!(project.getManager() == profil)) {
            throw new SecurityException("Vous n'êtes pas manager de ce projet");
        }

        // 3. et 4. Crée et ajoute chaque tâche à la liste du projet
        for (TaskInputDTO taskInput : tasksDTO.tasks()) {
            Task task = new Task();
            task.setTaskName(taskInput.taskName());
            task.setStatus(Status.TODO);
            task.setCreatedDate(LocalDate.now());
            task.setProject(project);
            project.getTasks().add(task);
        }

        // 5. Sauvegarde le projet avec les nouvelles tâches
        projectRepo.save(project);

        // 6. Retourne une confirmation
        return "Tâches créées avec succès pour le projet  : " + project.getTitle();
    }

    public String assignTasksToProfil( AssignTasksDTO assignDTO, Long managerProfilId) {



        Profil profilManager =  profilRepo.findById(managerProfilId)
                .orElseThrow(() -> new EntityNotFoundException("Profil non trouvé pour le manager ID : " + managerProfilId));

        Project project = projectRepo.findById(assignDTO.projectId())
                .orElseThrow(() -> new EntityNotFoundException("Project  non trouvé avec ID : " + assignDTO.projectId()));

        if (project.getStatus() == Status.VALIDATED){
            throw  new RuntimeException("Projet déjà terminé :) Veillez faire demande de Re-Ouvertir avec les Admins ");
        }

        if (!profilManager.getProfilName().equals(ProfilType.MANAGER)) {
            throw new SecurityException("Seuls les managers peuvent attribuer des tâches.");
        }
        if (!(profilManager == project.getManager() )) {
            throw new SecurityException("Vous n'êtes pas manager de ce projet");
        }

        // 2. Validation des entrées
        Profil profilCible = profilRepo.findById(assignDTO.profilIdCible())
                .orElseThrow(() -> new EntityNotFoundException("Profil cible non trouvé avec l'Id : " + assignDTO.profilIdCible()));

        if (!project.getMembers().contains(profilCible) ) {
            throw new SecurityException("Contributeur non associé au projet");
        }
        if (profilCible.getProfilName() != ProfilType.DEVELOPER && profilCible.getProfilName() != ProfilType.DESIGNER){
            throw  new IllegalArgumentException("Seuls les Developpeurs | Designers peuvent récevoirs des tâches !");
        }

        // 3. Attribution des tâches
        int tasksAssigned = 0;
        for (Long taskId : assignDTO.taskIds()) {
            Task task = taskRepo.findById(taskId)
                    .orElseThrow(() -> new EntityNotFoundException("Tâche inexistante avec l'Id : " + taskId));

            if (task.getProject().getId().equals(project.getId())) {
                if (task.getProfil() != null) {
                    throw new IllegalStateException("Tâche déjà attribuée avec l'Id : " + taskId);
                }
                task.setProfil(profilCible);
                profilCible.getTasks().add(task);
                task.setStatus(Status.IN_PROGRESS);
                project.setStatus(Status.IN_PROGRESS);
                tasksAssigned++;
            } else {
                throw new IllegalArgumentException("Tâche avec l'Id : " + taskId + " non associée au projet");
            }
        }

        // 4. Persistance
        projectRepo.save(project);
        profilRepo.save(profilCible);

        // 5. Réponse
        return "Tâches attribuées avec succès au profil ID : " + profilCible.getId() +
                " pour le projet ID : " + project.getId() + " (Nombre : " + tasksAssigned + ")";
    }



    public String submitTask(Long taskId, Long contributorId) {

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tâche non trouvée avec l'Id : " + taskId));

        // Vérifie que le profil de la tâche appartient bien à ce contributeur
        if (task.getProfil() == null || task.getProfil().getUser() == null ||
                !task.getProfil().getUser().getId().equals(contributorId)) {
            throw new SecurityException("Vous assayez de soumettre une tâche qui ne vous appartient pas." );
        }
        if (task.getStatus() == Status.DONE){
            throw new RuntimeException("Cette tâche a déjà été soumis");
        }

        task.setStatus(Status.DONE);
        task.setCreatedDate(LocalDate.now());

        taskRepo.save(task);

        return "Tâche ID " + taskId + " soumise avec succès.";
    }
}