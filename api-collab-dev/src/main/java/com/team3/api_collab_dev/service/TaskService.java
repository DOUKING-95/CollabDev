package com.team3.api_collab_dev.service;

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
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaskService {

    private final TaskRepo taskRepo;
    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;
    private final ProfilRepo profilRepo;

    public TaskService(TaskRepo taskRepo, ProjectRepo projectRepo, UserRepo userRepo, ProfilRepo profilRepo) {
        this.taskRepo = taskRepo; // Initialisation des repositories
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
        this.profilRepo = profilRepo;
    }

    public String createTasks(Long userId, CreateTasksDTO tasksDTO) {
        // 1. Vérifie si l'utilisateur a un profil manager
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'Id : " + userId));
        Profil profil = (Profil) profilRepo.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Profil non trouvé pour l'utilisateur ID : " + userId));
        if (!profil.getProfilName().equals(ProfilType.MANAGER)) {
            throw new SecurityException("Seuls les managers peuvent créer des tâches.");
        }

        // 2. Valide le projet et l'association du profil
        Project project = projectRepo.findById(tasksDTO.projectId())
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'Id : " + tasksDTO.projectId()));
        if (!project.getContributionRequests().contains(profil)) {
            throw new SecurityException("Le profil n'est pas associé au projet.");
        }

        // 3. et 4. Crée et ajoute chaque tâche à la liste du projet
        for (TaskInputDTO taskInput : tasksDTO.tasks()) {
            Task task = new Task();
            task.setTaskName(taskInput.taskName());
            task.setStatus(Status.TODO); // Statut par défaut défini sur TODO
            task.setCreatedDate(LocalDate.now());
            task.setProject(project);

            project.getTasks().add(task); // Ajoute à la liste des tâches du projet
        }

        // 5. Sauvegarde le projet avec les nouvelles tâches
        projectRepo.save(project);

        // 6. Retourne une confirmation
        return "Tâches créées avec succès pour le projet ID : " + tasksDTO.projectId();
    }
}