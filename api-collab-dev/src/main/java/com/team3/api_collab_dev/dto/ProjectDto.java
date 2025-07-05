package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.Domain;
import jakarta.validation.constraints.NotBlank;

public record ProjectDto(

        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotBlank
        Domain domain,
        String specification,

        @NotBlank
        User author) {
}
