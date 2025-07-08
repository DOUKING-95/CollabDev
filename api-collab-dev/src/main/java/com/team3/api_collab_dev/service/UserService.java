package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.Exception.ExistSameEmailException;
import com.team3.api_collab_dev.Exception.IncorrectPasswordException;
import com.team3.api_collab_dev.dto.ChangePasswordDTO;
import com.team3.api_collab_dev.dto.UserCreateDTO;
import com.team3.api_collab_dev.dto.UserUpdateDTO;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.mapper.UserMapper;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.TaskRepo;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private ProfilRepo profilRepo;

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


        return "Information mise à jour avec succès";
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

        if (Objects.equals(dto.newPassword(), dto.confirmPassword())){
            user.setPassword(passwordEncoder.encode(dto.newPassword()));

            userRepo.save(user);
            return  " :) Votre mot de passe à été  modifier avec success ";
        }
        else {
            return  " :) Les mots de passe ne sont pas identiques  ";
        }


    }


    public  User getUserById(Long userId){
        return this.userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException(" :) Oooops aucun utilisateur trouver avec l'id " + userId));
    }
    // add this members to contribution request  with his profil
    public String joinProjectWithProfilName(Long userId, ProfilType profilName, Long projectId) {


        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User non trouvé avec l'Id : " + userId));

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'Id : " + projectId));


        Optional<Profil> profilOptional = profilRepo.findByUserIdAndProfilName(userId, profilName.toString());

        Profil profil;
        if (profilOptional.isPresent()) {
            profil = profilOptional.get();
        } else {

            profil = new Profil();
            profil.setUser(user);
            profil.setProfilName(profilName);
            profil.setLevel(Level.BEGINNER);
            profil.setCoins(0);
            profil.setValidatedProjects(0);
            profil = profilRepo.save(profil);
        }


        if (!project.getContributionRequests().contains(profil)) {
            project.getContributionRequests().add(profil);
        }


        projectRepo.save(project);

        return "Profil associé avec succès au projet";
    }
}