package com.team3.api_collab_dev.mapper;

import com.team3.api_collab_dev.dto.CommentDto;
import com.team3.api_collab_dev.entity.Comment;

public class CommentMapper {

    private final UserMapper userMapper = new UserMapper();


    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getAuthor() != null ? UserMapper.toDto (comment.getAuthor()) : null,
                comment.getProject() != null ? ProjectMapper.toDto(comment.getProject()) : null,
                comment.getContent()
        );
    }

    public static Comment toEntity(CommentDto dto) {
        if (dto == null) return null;
        Comment comment = new Comment();

        if (dto.user() != null) {
            comment.setAuthor(UserMapper.toEntity (dto.user()));
        }
        if (dto.project() != null) {
            comment.setProject(ProjectMapper.toEntity(dto.project()));
        }
        comment.setContent(dto.content());

        return comment;
    }}

