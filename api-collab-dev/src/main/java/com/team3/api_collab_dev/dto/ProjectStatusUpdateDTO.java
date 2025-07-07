package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.enumType.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectStatusUpdateDTO {
    private Long projetId;
    private Status nouveauStatus;
}
