package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.enumType.StatusLivrable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivrableResponseDTO {
        private Long id;
        private String description;
        private String auteurPseudo;
        private String projetTitre;
        private String statut;
    }

