package com.team3.api_collab_dev.mapper;

import com.team3.api_collab_dev.dto.ProfilDto;
import com.team3.api_collab_dev.entity.Profil;

import java.util.List;
import java.util.stream.Collectors;

public class ProfilMapper {

    static UserMapper userMapper = new UserMapper();

    /**
     * Convertit une entité Profil en ProfilDto
     */
    public static ProfilDto toDto(Profil profil) {
        if (profil == null) return null;

        return new ProfilDto(
                profil.getId(),
                profil.getUser() != null ? profil.getUser().getId() : null,
                profil.getLevel(),
                profil.getCoins(),
                profil.getValidatedProjects(),
                profil.getBadge(),
                profil.getProfilName(),
                profil.getTasks() != null
                        ? profil.getTasks().stream().map(TaskMapper::toDto).collect(Collectors.toList())
                        : List.of(),
                profil.getRequestedProjects() != null
                        ? profil.getRequestedProjects().stream().map(ProjectMapper::toDto).collect(Collectors.toList())
                        : List.of(),
                profil.getCreatedDate()
        );
    }

    /**
     * Convertit un ProfilDto en entité Profil
     */
    public static Profil toEntity(ProfilDto dto) {
        if (dto == null) return null;

        Profil profil = new Profil();
        profil.setId(dto.id());



        profil.setLevel(dto.level());
        profil.setCoins(dto.coins());
        profil.setValidatedProjects(dto.validatedProjects());
        profil.setBadge(dto.badge());
        profil.setProfilName(dto.profilName());

        if (dto.taskIds() != null) {
            profil.setTasks(
                    dto.taskIds().stream()
                            .map(TaskMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }

        if (dto.requestedProjectIds() != null) {
            profil.setRequestedProjects(
                    dto.requestedProjectIds().stream()
                            .map(ProjectMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }

        profil.setCreatedDate(dto.createdDate());

        return profil;
    }
}