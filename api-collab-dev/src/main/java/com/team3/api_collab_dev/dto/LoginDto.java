package com.team3.api_collab_dev.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @NotBlank(message = "L'email ne peut pas vide ")
        @Email(message = "Merci de respecter le format email (exampl@gmail.com) !")
        String email,

        @NotBlank(message = "Le mot de passe ne peut etre vide !")
                @Size(min = 8, message = "Donner un mot de passe forte avec plus de huit(8) caractère")
        String password) {
}
