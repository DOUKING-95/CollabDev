package com.team3.api_collab_dev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebPushRequest {
    private String endpoint;
    private String publicKey;
    private String auth;
    private String payload;

    // Getters + Setters
}

