package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.enumType.ValidationType;

import java.time.LocalDate;

public record TaskDto(
        Long id,
        Long profilId,
        Long projectId,
        double coins,
        String taskName,
        String taskDescription,
        Status status,
        LocalDate createdDate
) {}
