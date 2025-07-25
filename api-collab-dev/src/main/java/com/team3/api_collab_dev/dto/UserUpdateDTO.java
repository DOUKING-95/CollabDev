package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(

        @NotBlank
        @Size(max = 50,message = "Le speudo ne peut dépasser 50 caractères")
        String speudo,
        @Email
        String email
        ) {
}
