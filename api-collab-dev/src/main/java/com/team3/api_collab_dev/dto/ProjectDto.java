package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.enumType.Level;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDTO {

    private Long id;
    private String title;
    private Level level;

    public ProjectDTO(Long id, String title, Level level) {
        this.id = id;
        this.title = title;
        this.level = level;
    }
}
