package com.team3.api_collab_dev.dto;

import java.util.List;

public record CreateTasksDTO(
        Long projectId,
        List<TaskInputDTO> tasks)
{

}
