package com.team3.api_collab_dev.dto;

public record ChangePasswordDTO(
        String oldPassword,
        String newPassword,
        String confirmPassword
        ) {

}
