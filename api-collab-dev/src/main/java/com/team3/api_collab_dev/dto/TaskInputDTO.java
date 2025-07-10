package com.team3.api_collab_dev.dto;

import java.time.LocalDate;

public record TaskInputDTO(
        String taskName,
        String description,
        LocalDate deadLine) {}

