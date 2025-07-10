package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.entity.CommentTask;
import com.team3.api_collab_dev.service.CommentTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/comment-tasks")
public class CommentTaskController {

    @Autowired
    private CommentTaskService commentTaskService;

    @PostMapping
    public CommentTask addComment(
            @PathVariable Long taskId,
            @RequestBody AddCommentTaskRequest request){
    return commentTaskService.addComment(taskId, request.getContent(), request.getUserId());
    }
    @GetMapping
    public List<CommentTask> getComments(@PathVariable Long taskId) {
        return commentTaskService.getCommentsByTask(taskId);
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