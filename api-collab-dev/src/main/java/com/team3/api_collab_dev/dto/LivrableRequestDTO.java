package com.team3.api_collab_dev.dto;

import lombok.Data;

@Data
public class LivrableRequestDTO {

    private String description;
    private Long auteurId;
    private Long projetId;
}
