package com.team3.api_collab_dev.dto;

import java.time.LocalDate;
import java.util.List;

public record CreateTasksDTO(
        String taskName,
        String description,
        LocalDate deadLine) {}
