package com.team3.api_collab_dev.mapper;

import com.team3.api_collab_dev.dto.*;
import com.team3.api_collab_dev.entity.Comment;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.Domain;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserMapper {

    public User dtoToUser(UserCreateDTO userCreateDTO) {

        User user = new User();

        user.setPseudo(userCreateDTO.speudo());
        user.setEmail(userCreateDTO.email());
        user.setPassword(userCreateDTO.password());
        user.setRole(userCreateDTO.role());
        return user;
    }


    public UserResponseDTO userToDto(User user) {
        return new UserResponseDTO(user.getId(), user.getPseudo(), user.getEmail(), user.getRole());
    }

    public void updateEntity(User user, UserUpdateDTO dto) {
        user.setPseudo(dto.speudo());
        user.setEmail(dto.email());

    }

    public UserCreateDTO userToCreateDto(User user) {
        return new UserCreateDTO(
                user.getPseudo(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    public UserResponseDTO userToUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getPseudo(),
                user.getEmail(),
                user.getRole()
        );
    }

    public ProjectDto projectToDto(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getDomaine(),
                project.getSpecification(),
                userToUserResponseDTO(project.getAuthor()),
                project.getManager(),
                project.getStatus(),
                project.getLevel(),
                project.getGithubLink(),
                project.getTasks(),
                project.getMembers(),
                project.getPendingProfiles(),
                project.getCoins(),
                project.getComments(),
                project.getContributionRequests(),
                project.getCreatedDate()

        );
    }


    public Comment dtoToComment(CommentDto commentDto) {
        Comment comment = new Comment();

        comment.setAuthor(commentDto.user());
        comment.setProject(commentDto.project());
        comment.setContent(commentDto.content());
        return comment;
    }


}
