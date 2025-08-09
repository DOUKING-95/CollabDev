package com.team3.api_collab_dev.mapper;

import com.team3.api_collab_dev.dto.UserCreateDTO;
import com.team3.api_collab_dev.dto.UserResponseDTO;
import com.team3.api_collab_dev.dto.UserUpdateDTO;
import com.team3.api_collab_dev.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class UserMapper {

    /**
     * Convertit un DTO de création en entité User
     */
    public User fromCreateDto(UserCreateDTO userCreateDTO) {
        if (userCreateDTO == null) return null;

        User user = new User();
        user.setPseudo(userCreateDTO.speudo());
        user.setEmail(userCreateDTO.email());
        user.setPassword(userCreateDTO.password());
        return user;
    }

    /**
     * Convertit une entité User en DTO de réponse
     */
    public static UserResponseDTO toDto(User user) {
        if (user == null) return null;

        return new UserResponseDTO(
                user.getId(),
                user.getPseudo(),
                user.getEmail(),
                user.getProfils() != null
                        ? user.getProfils().stream()
                        .map(ProfilMapper::toDto)
                        .collect(Collectors.toList())
                        : null,
                user.getRole()
        );
    }

    /**
     * Convertit une entité User en DTO de création
     */
    public UserCreateDTO toCreateDto(User user) {
        if (user == null) return null;

        return new UserCreateDTO(
                user.getPseudo(),
                user.getEmail(),
                user.getPassword()
        );
    }

    /**
     * Convertit un DTO de réponse en entité User
     */
    public static User toEntity(UserResponseDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.id());
        user.setPseudo(dto.speudo());
        user.setEmail(dto.email());
        user.setRole(dto.role());

        if (dto.profils() != null) {
            user.setProfils(
                    dto.profils().stream()
                            .map(ProfilMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }

        return user;
    }

    public void updateEntity(User user, UserUpdateDTO dto) {
        user.setPseudo(dto.speudo());
        user.setEmail(dto.email());

    }
}



 /**   public static ProjectDto projectToDto(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getDomaine(),
                project.getSpecification(),
                userToDto(project.getAuthor()),

                project.getManager() != null ? ProfilMapper.toDto(project.getManager()) : null,

                project.getStatus(),
                project.getLevel(),
                project.getGithubLink(),

                project.getTasks() != null
                        ? project.getTasks().stream()
                        .filter(Objects::nonNull)
                        .map(TaskMapper::toDto) // ⬅️ conversion en TaskDto
                        .toList()
                        : List.of(),

                project.getMembers() != null
                        ? project.getMembers().stream()
                        .filter(Objects::nonNull)
                        .map(ProfilMapper::toDto)
                        .toList()
                        : List.of(),

                project.getPendingProfiles() != null
                        ? project.getPendingProfiles().stream()
                        .filter(Objects::nonNull)
                        .map(ProfilMapper::toDto)
                        .toList()
                        : List.of(),

                project.getCoins(),

                project.getComments() != null
                        ? project.getComments().stream()
                        .map(CommentMapper::toDto) // ⬅️ idem pour éviter la boucle
                        .toList()
                        : List.of(),

                project.getContributionRequests() != null
                        ? project.getContributionRequests().stream()
                        .filter(Objects::nonNull)
                        .map(ProfilMapper::toDto)
                        .toList()
                        : List.of(),

                project.getCreatedDate()
        );
    }*/








