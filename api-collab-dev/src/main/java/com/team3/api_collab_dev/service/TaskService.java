package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.dto.AssignTasksDTO;
import com.team3.api_collab_dev.dto.CreateTasksDTO;
import com.team3.api_collab_dev.dto.TaskDto;
import com.team3.api_collab_dev.dto.TaskInputDTO;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.enumType.ValidationType;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.TaskRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;
    private final ProjectRepo projectRepo;
    private final ProfilRepo profilRepo;

    public TaskDto createTask(Long projectId, CreateTasksDTO dto) {
        if (dto == null || dto.taskName() == null || dto.taskName().isBlank()) {
            throw new IllegalArgumentException("Le nom de la tâche est obligatoire.");
        }

        // Récupérer le projet via projectId passé dans l'URL
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'ID : " + projectId));

        // Vérifier le manager du projet
        Profil manager = project.getManager();
        if (manager == null) {
            throw new IllegalStateException("Le projet n'a pas encore de manager assigné.");
        }
        if (!ProfilType.MANAGER.equals(manager.getProfilName())) {
            throw new SecurityException("Le profil assigné n'est pas un manager.");
        }

        // Création de la tâche
        Task task = new Task();
        task.setTaskName(dto.taskName());
        task.setDescription(dto.description());
        task.setStatus(Status.TODO);
        task.setCreatedDate(LocalDate.now());
        task.setDeadLine(dto.deadLine());
        task.setProject(project);
        task.setProfil(manager);

        Task savedTask = taskRepo.save(task);

        return new TaskDto(
                savedTask.getId(),
                manager.getId(),
                project.getId(),
                savedTask.getCoins(),
                savedTask.getTaskName(),
                savedTask.getStatus(),
                savedTask.getIsValid(),
                savedTask.getCreatedDate()
        );
    }


    public String assignTasksToProfil(AssignTasksDTO assignDTO, Long managerProfilId) {


        Profil profilManager = profilRepo.findById(managerProfilId)
                .orElseThrow(() -> new EntityNotFoundException("Profil non trouvé pour le manager ID : " + managerProfilId));

        Project project = projectRepo.findById(assignDTO.projectId())
                .orElseThrow(() -> new EntityNotFoundException("Project  non trouvé avec ID : " + assignDTO.projectId()));

        if (project.getStatus() == Status.VALIDATED) {
            throw new RuntimeException("Projet déjà terminé :) Veillez faire demande de Re-Ouvertir avec les Admins ");
        }

        if (!profilManager.getProfilName().equals(ProfilType.MANAGER)) {
            throw new SecurityException("Seuls les managers peuvent attribuer des tâches.");
        }
        if (!(profilManager == project.getManager())) {
            throw new SecurityException("Vous n'êtes pas manager de ce projet");
        }

        // 2. Validation des entrées
        Profil profilCible = profilRepo.findById(assignDTO.profilIdCible())
                .orElseThrow(() -> new EntityNotFoundException("Profil cible non trouvé avec l'Id : " + assignDTO.profilIdCible()));

        if (!project.getMembers().contains(profilCible)) {
            throw new SecurityException("Contributeur non associé au projet");
        }
        if (profilCible.getProfilName() != ProfilType.DEVELOPER && profilCible.getProfilName() != ProfilType.DESIGNER) {
            throw new IllegalArgumentException("Seuls les Developpeurs | Designers peuvent récevoirs des tâches !");
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

        Profil profil = profilRepo.findById(contributorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profil non trouvée avec l'Id : " + contributorId));

        // Vérifie que le profil de la tâche appartient bien à ce contributeur
        if (task.getProfil() != profil) {
            throw new SecurityException("Vous assayez de soumettre une tâche qui ne vous appartient pas.");
        }
        if (task.getStatus() == Status.DONE) {
            throw new RuntimeException("Cette tâche a déjà été soumis");
        }

        task.setStatus(Status.DONE);
        task.setCreatedDate(LocalDate.now());

        taskRepo.save(task);

        return "Tâche ID " + taskId + " soumise avec succès.";
    }
}