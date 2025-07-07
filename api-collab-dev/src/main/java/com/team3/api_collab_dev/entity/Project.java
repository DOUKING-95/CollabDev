package com.team3.api_collab_dev.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team3.api_collab_dev.enumType.Domain;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Domain domaine;

    private String specification;

    @ManyToOne
    @JsonBackReference
    private User gestionnaire;

    @OneToOne
    private User author;

    @OneToOne
    private User manager;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Level level;

    private String githubLink;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Comment> comments;


    private LocalDate createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

}
