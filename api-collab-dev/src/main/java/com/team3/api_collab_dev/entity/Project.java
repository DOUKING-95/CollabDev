package com.team3.api_collab_dev.entity;

import com.team3.api_collab_dev.enumType.Domain;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    @OneToOne
    private User author;

    @OneToOne
    private User manager;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Level level;

    private String githubLink;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    private double coins;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "project_contribution_requests",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "profil_id")
    )
    private List<Profil> contributionRequests;


    private LocalDate createdDate;

    public Project(String title, String description, Domain domain, String specification, User author) {
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }


}
