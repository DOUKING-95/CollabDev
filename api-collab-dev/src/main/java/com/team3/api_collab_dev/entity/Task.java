package com.team3.api_collab_dev.entity;

import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.enumType.ValidationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profil_id")
    private Profil profil;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private double coins;

    @Column(nullable = false)
    private String taskName;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private ValidationType isValid;


    private LocalDate createdDate;

    private LocalDate DeadLine;


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }
}

