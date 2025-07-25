package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.dto.CommentDto;
import com.team3.api_collab_dev.entity.Comment;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.mapper.UserMapper;
import com.team3.api_collab_dev.repository.CommentRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private CommentRepo commentRepo;
    private ProjectRepo projectRepo;
    private UserRepo userRepo;
    private UserMapper userMapper;

    public Comment makeComment(Long projectId, Long userId, CommentDto comment) {
        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Pas de project avec cet id : " + projectId));

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Pas de user avec cet id : " + userId));

        return this.commentRepo.save(userMapper.dtoToComment(comment));
    }


    public void deleteComment(Long commentId) {

        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Aucun commentaire avec cet id : " + commentId));

        Project project = comment.getProject();
        if (project != null) {
            project.getComments().remove(comment);
            this.projectRepo.save(project);
        }

        this.commentRepo.delete(comment);
    }
}

