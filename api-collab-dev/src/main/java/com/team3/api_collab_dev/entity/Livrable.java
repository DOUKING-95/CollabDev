package com.team3.api_collab_dev.entity;

import com.team3.api_collab_dev.enumType.StatusLivrable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livrable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    private User auteur;  // contributeur

    @ManyToOne
    private Project projet;

    @Enumerated(EnumType.STRING)
    private StatusLivrable statut;

    private LocalDate dateAttribution;

    private LocalDate dateLivraison;
}
