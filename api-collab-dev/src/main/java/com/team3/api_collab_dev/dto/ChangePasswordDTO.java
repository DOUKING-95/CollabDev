package com.team3.api_collab_dev.dto;

import jakarta.validation.constraints.NotNull;

public record ChangePasswordDTO(
        @NotNull(message = "Merci de saisir votre ancien mot de passe")
        String oldPassword,
        @NotNull(message = "Merci de saisir votre nouveau  mot de passe")
        String newPassword,
        @NotNull(message = "Merci de confirmer  votre nouveau mot de passe")
        String confirmPassword
        ) {

}
