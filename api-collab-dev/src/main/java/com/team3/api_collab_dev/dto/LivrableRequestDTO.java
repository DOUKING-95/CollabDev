package com.team3.api_collab_dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivrableRequestDTO {
    private String description;
    private Long auteurId;  // contributeur
    private Long projetId;
}
