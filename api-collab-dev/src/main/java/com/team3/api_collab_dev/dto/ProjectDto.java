package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.Domain;

public record ProjectDto(
        String title,
        String description,
        Domain domain,
        String specification,
        User author

) {
}
