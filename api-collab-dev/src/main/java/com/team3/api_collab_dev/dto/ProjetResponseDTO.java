package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.enumType.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjetResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Status status;
}
