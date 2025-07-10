package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.enumType.ProfilType;
import lombok.Data;

@Data
public class JoinRequestDto {
    private Long userId;
    private Long projectId;
    private ProfilType profilType;
}
