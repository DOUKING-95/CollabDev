package com.team3.api_collab_dev.mapper;

import com.team3.api_collab_dev.dto.ProjectDto;
import com.team3.api_collab_dev.entity.Project;
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
}


