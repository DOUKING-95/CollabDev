package com.team3.api_collab_dev.mapper;

import com.team3.api_collab_dev.dto.ProjectDto;
import com.team3.api_collab_dev.entity.Project;


import java.util.function.Function;

public class ProjectMapper implements Function<ProjectDto, Project> {

    @Override
    public Project apply(ProjectDto projectDto) {
        return new Project(projectDto.title(), projectDto.description(), projectDto.domain(), projectDto.specification(), projectDto.author());
    }
}
