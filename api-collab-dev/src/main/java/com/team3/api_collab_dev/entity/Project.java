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


import java.util.ArrayList;
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

    private String specification; //cahier de charges

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Profil manager;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Level level;



    private String githubLink;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "profil_id")
    )
    private List<Profil> members = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "project_pending_profiles",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "profil_id")
    )
    private List<Profil> pendingProfiles = new ArrayList<>();

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
        this.title = title;
        this.description = description;
        this.domaine = domain;
        this.specification = specification;
        this.author = author;
    }


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }


}
