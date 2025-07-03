package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.enumType.Domain;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {
    private String title;
    private String description;
    private Domain domaine;
    private String specification;
    private Status status;
    private Level level;
    private String githubLink;
    private Long authorId;
    private Long managerId;
}
