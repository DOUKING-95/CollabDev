package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.entity.ManagerInfo;
import com.team3.api_collab_dev.enumType.ProfilType;

public record JoinProjectRequest(
        Long userId,
         Long projectId,
        ProfilType profilType,
        String githubLink

) {
}
