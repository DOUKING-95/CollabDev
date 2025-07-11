package com.team3.api_collab_dev.dto;

import java.util.List;

public record AssignTasksDTO(Long projectId,
                             Long profilIdCible,
                             List<Long> taskIds) {
}
