package com.team3.api_collab_dev.entity;

import com.team3.api_collab_dev.enumType.ContributionsStatusType;
import com.team3.api_collab_dev.enumType.ContributionsType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "contributions")
public class Contributions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;

    @ManyToOne
    @JoinColumn(name= "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ContributionsType type;
    @Enumerated(EnumType.STRING)
    private ContributionsStatusType statusType ;
    private int awaredPoints;
}
