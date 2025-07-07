package com.team3.api_collab_dev.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivrableResponseDTO {
    private Long id;
    private String description;
    private String auteurNom;
    private String projetTitre;
}
