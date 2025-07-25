package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.CommentTask;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.repository.CommentTaskRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.TaskRepo;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentTaskService {


    private CommentTaskRepo commentTaskRepo;
    private TaskRepo taskRepo;
    private UserRepo userRepo;
    private ProjectRepo projectRepo;

    public CommentTask addComment(Long taskId, String content, Long userId) {

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Tâche non trouver avec l'Id : " + taskId));

        Project project = projectRepo.findById(task.getProject().getId())
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'Id : " + task.getProject().getId()));

        User commenter = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouver avec l'Id : " + userId));

        boolean isAuthor = project.getAuthor().getId().equals(userId);

        boolean isManager = project.getManager() != null &&
                project.getManager().getId().equals(userId);

        boolean isMember = project.getMembers().stream()
                .anyMatch(profil -> profil.getUser().getId().equals(userId));

        if (!isAuthor && !isManager && isMember) {
            throw new SecurityException("Seuls l'auteur, le gestionnaire ou les membres peuvent commenter les tâches");
        }

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Le contenu du commentaire ne peut pas être vide ");
        }

        CommentTask commentTask = new CommentTask(content, task, commenter);
        return commentTaskRepo.save(commentTask);
    }

    public List<CommentTask> getCommentsByTask(Long taskId) {
        return commentTaskRepo.findByTaskId(taskId);
    }
}