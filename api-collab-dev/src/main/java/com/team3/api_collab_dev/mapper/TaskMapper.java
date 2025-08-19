package com.team3.api_collab_dev.mapper;

import com.team3.api_collab_dev.dto.TaskDto;
import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;

public class TaskMapper {

    public static TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }

        return new TaskDto(
                task.getId(),
                task.getProfil() != null ? task.getProfil().getId() : null,
                task.getProject() != null ? task.getProject().getId() : null,
                task.getCoins(),
                task.getTaskName(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedDate()
        );
    }

    public static Task toEntity(TaskDto dto) {
        if (dto == null) {
            return null;
        }

        Task task = new Task();
        task.setId(dto.id());

        // On crée des objets Profil et Project avec uniquement l'ID pour éviter la récursion
        if (dto.profilId() != null) {
            Profil profil = new Profil();
            profil.setId(dto.profilId());
            task.setProfil(profil);
        }

        if (dto.projectId() != null) {
            Project project = new Project();
            project.setId(dto.projectId());
            task.setProject(project);
        }

        task.setCoins(dto.coins());
        task.setTaskName(dto.taskName());
        task.setStatus(dto.status());
        task.setCreatedDate(dto.createdDate());

        return task;
    }
}
