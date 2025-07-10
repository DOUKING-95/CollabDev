package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.entity.CommentTask;
import com.team3.api_collab_dev.service.CommentTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("commentTasks")
public class CommentTaskController {

    @Autowired
    private CommentTaskService commentTaskService;

    @PostMapping
    public CommentTask addComment(
            @PathVariable Long taskId,
            @RequestBody AddCommenTaskRequest request){
    return commentTaskService.addComment(taskId, request.getContent(), request.getUserId());
    }

    public List<CommentTask> getComments(@PathVariable Long taskId){
        return commentTaskService.getCommentsByTask(taskId);
    }
}

//Classe pour gérer la requête POST
class AddCommenTaskRequest{
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