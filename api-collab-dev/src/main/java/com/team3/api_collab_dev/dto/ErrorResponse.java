package com.team3.api_collab_dev.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        String code,
        String error,
        String message,
        String path) {
}
