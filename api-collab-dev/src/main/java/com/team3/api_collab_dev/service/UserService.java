package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.Exception.ExistSameEmailException;
import com.team3.api_collab_dev.Exception.IncorrectPasswordException;
import com.team3.api_collab_dev.dto.ChangePasswordDTO;
import com.team3.api_collab_dev.dto.UserCreateDTO;
import com.team3.api_collab_dev.dto.UserUpdateDTO;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.mapper.UserMapper;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.TaskRepo;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private ProfilRepo profilRepo;
    private TaskRepo taskRepo;
    private ProjectRepo projectRepo;


    public String createUser(UserCreateDTO userDto) {
        Optional<User> optionalUser = this.userRepo.findByEmail(userDto.email());

        if (optionalUser.isPresent())
            throw new ExistSameEmailException("Un Compte existe déjà avec ce email ! :) Merci de revoir votre email.");

        User user = new User();
        user.setPseudo(userDto.speudo());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole(userDto.role());
        this.userRepo.save(user);
        return " :) Utilsateur ===" + userDto.speudo() + "=== créer avec succes";
    }

    public String updateUserInfo(Long userId, UserUpdateDTO userInfo) {
        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Pas de user avec ID: " + userId));

        userMapper.updateEntity(existingUser, userInfo);

        userRepo.save(existingUser);

        return " :) Vos infos ont été misent à jour avec succes";
    }

    public Iterable<User> getAllUsers(){
        return  this.userRepo.findAll();
    }



    public User login(String email, String password) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cet email est incorrect ! :) Merci de réverifier  "));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException("Mot de passe incorrect ! :) Merci réverifier");
        }

        return user;
    }

    public String changePassword(Long userId, ChangePasswordDTO dto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(" :) User non trouver"));

        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            throw new IllegalArgumentException(" Votre  mot de passe incorrect ! :) Merci de réesayez ");
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));

        userRepo.save(user);
        return  " :) Votre mot de passe à été  modifier avec success ";
    }


    public  User getUserById(Long userId){
        return this.userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException(" :) Oooops aucun utilisateur trouver avec l'id " + userId));
    }
}