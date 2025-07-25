package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.Status;
import jakarta.validation.constraints.NotNull;

public record ConfigureProjectDto(

        @NotNull(message = "Merci de specifiez lz Niveau")
        Level level,
        @NotNull(message = "Merci Créer un Repo pour ce peojet de spécifier le lien ici")
        String githubLink,
        String specification


) {
}
