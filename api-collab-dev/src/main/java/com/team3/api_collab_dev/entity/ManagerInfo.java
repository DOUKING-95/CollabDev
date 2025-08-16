package com.team3.api_collab_dev.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "manager-info")
public class ManagerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private  Profil manager;

    @Column(nullable = false)
    private String githubLink;

    @Column(nullable = false)
    private  String pathCv;


    private LocalDate createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }
}
