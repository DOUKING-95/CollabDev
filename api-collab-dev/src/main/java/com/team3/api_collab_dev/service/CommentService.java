package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.entity.Comment;
import com.team3.api_collab_dev.repository.CommentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private CommentRepo commentRepo;

    public  Comment saveComment(Comment comment){
        return  this.commentRepo.save(comment);
    }

    public void deleteComment(Long commentId){
         this.commentRepo.deleteById(commentId);
    }
}
