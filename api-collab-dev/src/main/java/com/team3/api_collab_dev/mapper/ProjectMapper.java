package com.team3.api_collab_dev.mapper;

import com.team3.api_collab_dev.dto.ProjectDto;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.Task;
import org.springframework.stereotype.Component;


import java.util.function.Function;

import com.team3.api_collab_dev.dto.*;
import com.team3.api_collab_dev.entity.Project;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static ProjectDto toDto(Project project) {
        if (project == null) return null;

        return new ProjectDto(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getDomaine(),
                project.getSpecification(),
                UserMapper.toDto(project.getAuthor()),
                project.getManager() != null ? ProfilMapper.toDto(project.getManager()) : null,
                project.getStatus(),
                project.getLevel(),
                project.getGithubLink(),

                project.getTasks() != null
                        ? project.getTasks().stream()
                        .filter(Objects::nonNull)
                        .map(TaskMapper::toDto)
                        .collect(Collectors.toList())
                        : List.of(),

                project.getMembers() != null
                        ? project.getMembers().stream()
                        .filter(Objects::nonNull)
                        .map(ProfilMapper::toDto)
                        .collect(Collectors.toList())
                        : List.of(),

                project.getPendingProfiles() != null
                        ? project.getPendingProfiles().stream()
                        .filter(Objects::nonNull)
                        .map(ProfilMapper::toDto)
                        .collect(Collectors.toList())
                        : List.of(),

                project.getCoins(),

                project.getComments() != null
                        ? project.getComments().stream()
                        .filter(Objects::nonNull)
                        .map(CommentMapper::toDto)
                        .collect(Collectors.toList())
                        : List.of(),

                project.getContributionRequests() != null
                        ? project.getContributionRequests().stream()
                        .filter(Objects::nonNull)
                        .map(ProfilMapper::toDto)
                        .collect(Collectors.toList())
                        : List.of(),

                project.getCreatedDate()
        );
    }

    public static Project toEntity(ProjectDto dto) {
        if (dto == null) return null;

        Project project = new Project();
        project.setId(dto.id());
        project.setTitle(dto.title());
        project.setDescription(dto.description());
        project.setDomaine(dto.domain());
        project.setSpecification(dto.specification());
        project.setAuthor(UserMapper.toEntity(dto.author()));
        project.setManager(ProfilMapper.toEntity(dto.managerId()));
        project.setStatus(dto.status());
        project.setLevel(dto.level());
        project.setGithubLink(dto.githubLink());

        if (dto.tasks() != null) {
            project.setTasks(dto.tasks().stream()
                    .map(TaskMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        if (dto.members() != null) {
            project.setMembers(dto.members().stream()
                    .map(ProfilMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        if (dto.pendingProfiles() != null) {
            project.setPendingProfiles(dto.pendingProfiles().stream()
                    .map(ProfilMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        project.setCoins(dto.coins());

        if (dto.comments() != null) {
            project.setComments(dto.comments().stream()
                    .map(CommentMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        if (dto.contributionRequests() != null) {
            project.setContributionRequests(dto.contributionRequests().stream()
                    .map(ProfilMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        project.setCreatedDate(dto.createdDate());

        return project;
    }


    public static TaskDto toDto(Task task) {
        if (task == null) return null;

        return new TaskDto(
                task.getId(),
                task.getProfil() != null ? task.getProfil().getId() : null,
                task.getProject() != null ? task.getProject().getId() : null,
                task.getCoins(),
                task.getTaskName(),
                task.getStatus(),
                task.getIsValid(),
                task.getCreatedDate()
        );
    }

    public static Task toEntity(TaskDto dto) {
        if (dto == null) return null;

        Task task = new Task();
        task.setId(dto.id());
        task.setCoins(dto.coins());
        task.setTaskName(dto.taskName());
        task.setStatus(dto.status());
        task.setIsValid(dto.isValid());
        task.setCreatedDate(dto.createdDate());

        return task;
    }

    public static ProfilDto toDto(Profil profil) {
        if (profil == null) return null;

        String pseudo = profil.getUser() != null ? profil.getUser().getPseudo() : "Inconnu";

        return new ProfilDto(
                profil.getId(),
                profil.getUser() != null ? profil.getUser().getId() : null,
                pseudo, // <-- ici
                profil.getLevel(),
                profil.getCoins(),
                profil.getValidatedProjects(),
                profil.getBadge(),
                profil.getProfilName(),
                profil.getTasks() != null
                        ? profil.getTasks().stream()
                        .filter(Objects::nonNull)
                        .map(TaskMapper::toDto)
                        .collect(Collectors.toList())
                        : List.of(),
                profil.getRequestedProjects() != null
                        ? profil.getRequestedProjects().stream()
                        .filter(Objects::nonNull)
                        .map(ProjectMapper::toDto)
                        .collect(Collectors.toList())
                        : List.of(),
                profil.getCreatedDate()
        );
    }


    public static Profil toEntity(ProfilDto dto) {
        if (dto == null) return null;

        Profil profil = new Profil();
        profil.setId(dto.id());
        profil.setLevel(dto.level());
        profil.setCoins(dto.coins());
        profil.setValidatedProjects(dto.validatedProjects());
        profil.setBadge(dto.badge());
        profil.setProfilName(dto.profilName());
        profil.setCreatedDate(dto.createdDate());

        // ⚠️ Gestion des relations à compléter selon ton modèle (user, tasks, requestedProjects)
        return profil;
    }

}


