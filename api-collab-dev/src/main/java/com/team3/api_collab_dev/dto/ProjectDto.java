package com.team3.api_collab_dev.dto;

import com.team3.api_collab_dev.entity.Comment;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.Domain;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ProjectDto(
        Long id,
        @NotBlank(message = "Le titre du projet  est Obligatoire")
        String title,
        @NotBlank(message = "La description du projet  est Obligatoire")
        String description,
        @NotNull(message = "Veillez renseigner un domaine")
        Domain domain,
        String specification,

        @NotNull(message = "L'auteur est doit etre specifier")
        UserResponseDTO author,
        Profil managerId,

        Status status,
        Level level,

        String githubLink,

        List<Task> tasks,

        List<Profil> members,
        List<Profil> pendingProfiles,

        double coins,

        List<Comment> comments,

        List<Profil> contributionRequests,


        LocalDate createdDate

) {
}
