package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.enumType.Domain;
import com.team3.api_collab_dev.enumType.Level;

public record FilterProjectResponse(String title, String description, Domain domain, Level level) {
}
