package com.team3.api_collab_dev.entity;

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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(nullable = false)
    private String speudo;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

     private  String password;

     @OneToMany(mappedBy = "author" , fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
     private List<Project> projects;

     @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
     private  List<Profil> profils;


    private LocalDate createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    @Column(nullable = false)
    private Integer pointsBalance;

    @OneToOne
    @JoinColumn(name = "badge_id")
    private Badge currentBadge;




}
