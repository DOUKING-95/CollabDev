package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.entity.Role;
import com.team3.api_collab_dev.enumType.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(

        @NotBlank
        @Size(max = 50,message = "Le speudo ne peut dépasser 50 caractères")
        String speudo,
        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 8, message = "Le mot de passse doit superieur au egale à huit(8) caractères")
        String password,
        RoleType role) {
}
