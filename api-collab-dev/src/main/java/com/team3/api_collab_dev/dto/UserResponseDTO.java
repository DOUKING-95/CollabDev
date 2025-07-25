package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.entity.Role;
import com.team3.api_collab_dev.enumType.RoleType;

public record UserResponseDTO(
        Long id,
        String speudo,
        String email,
        RoleType role) {
}
