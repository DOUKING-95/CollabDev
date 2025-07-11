package com.team3.api_collab_dev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class CommentTaskDTO {
    private Long id;
    private String content;
    private Long taskId;
    private Long commenterId;
    private String commenterPseudo;
    private LocalDateTime createdDate;
}