package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.enumType.BadgeType;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.ProfilType;

import java.time.LocalDate;
import java.util.List;

public record ProfilDto(
        Long id,
        Long userId,
        Level level,
        double coins,
        int validatedProjects,
        BadgeType badge,
        ProfilType profilName,
        List<TaskDto> taskIds,
        List<ProjectDto> requestedProjectIds,
        LocalDate createdDate
) {}
