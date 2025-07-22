package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.CommentTaskDTO;
import com.team3.api_collab_dev.entity.CommentTask;
import com.team3.api_collab_dev.service.CommentTaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tasksComment")
@Tag(name = "TaskComment", description = "Task comment controller ")
public class CommentTaskController {

    @Autowired
    private CommentTaskService commentTaskService;

    @PostMapping("/{taskId}/comment-tasks")
    public CommentTaskDTO addComment(
            @PathVariable(name = "taskId") Long taskId,
            @RequestBody AddCommentTaskRequest request){
        CommentTask commentTask = commentTaskService
                .addComment(taskId, request.getContent(), request.getUserId());
        return new CommentTaskDTO(
                commentTask.getId(),
                commentTask.getContent(),
                commentTask.getTask().getId(),
                commentTask.getCommenter().getId(),
                commentTask.getCommenter().getPseudo(),
                commentTask.getCreatedDate()
        );

    }
    @GetMapping
    public List<CommentTaskDTO> getComments(@PathVariable Long taskId) {
        List<CommentTask> commentTasks = commentTaskService.getCommentsByTask(taskId);
        return commentTasks.stream()
                .map(commentTask-> new CommentTaskDTO(
                        commentTask.getId(),
                        commentTask.getContent(),
                        commentTask.getTask().getId(),
                        commentTask.getCommenter().getId(),
                        commentTask.getCommenter().getPseudo(),
                        commentTask.getCreatedDate()

                )).collect(Collectors.toList());
    }

}

//Classe pour gérer la requête POST
class AddCommentTaskRequest{
    private String content;
    private Long userId;

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}