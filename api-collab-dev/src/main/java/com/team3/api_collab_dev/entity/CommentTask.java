package com.team3.api_collab_dev.entity;

import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "comment_task")
public class CommentTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User commenter;

    private LocalDateTime createdDate;

    public CommentTask(String content, Task task, User commenter) {
        this.content = content;
        this.task = task;
        this.commenter = commenter;
        this.createdDate = LocalDateTime.now();
    }
}
