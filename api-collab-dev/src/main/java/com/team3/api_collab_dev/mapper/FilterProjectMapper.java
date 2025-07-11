package com.team3.api_collab_dev.mapper;

import com.team3.api_collab_dev.dto.FilterProjectResponse;
import com.team3.api_collab_dev.entity.Project;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public  class FilterProjectMapper  implements Function<Project , FilterProjectResponse> {
    @Override
    public FilterProjectResponse apply(Project project) {
        return new FilterProjectResponse(
                project.getTitle(),
                project.getDescription(),
                project.getDomaine(),
                project.getLevel()
        );
    }
}
