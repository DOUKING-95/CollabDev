package com.team3.api_collab_dev.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Livrable {
    @Id
    @GeneratedValue
    private Long id;
    private String description;

    @ManyToOne
    private User auteur;

    @ManyToOne
    private Project projet;
}

