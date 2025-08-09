package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentDto(
        @NotNull(message = "Merci de specifier ID votre ID pour qu' il puisse etre associer au commentaire")
        UserResponseDTO user,
        @NotNull(message = "Merci ce specifier ID du project en question")
        ProjectDto project,

        @NotBlank(message = "message obligatoire lors du commentaire ")
        String content
) {
}


