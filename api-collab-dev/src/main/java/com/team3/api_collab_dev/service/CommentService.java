package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.entity.Comment;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.repository.CommentRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private CommentRepo commentRepo;
    private ProjectRepo projectRepo;

    public Comment makeComment(Long projectId, Comment comment) {
        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Pas de project avec cet id : " + projectId));

        comment.setProject(project);
        Comment savedComment = this.commentRepo.save(comment);
        project.getComments().add(savedComment);
        this.projectRepo.save(project);

        return savedComment;
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

