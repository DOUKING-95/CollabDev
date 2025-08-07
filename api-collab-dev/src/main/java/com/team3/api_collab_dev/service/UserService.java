package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.Exception.ExistSameEmailException;
import com.team3.api_collab_dev.Exception.IncorrectPasswordException;
import com.team3.api_collab_dev.dto.ChangePasswordDTO;
import com.team3.api_collab_dev.dto.UserCreateDTO;
import com.team3.api_collab_dev.dto.UserUpdateDTO;
import com.team3.api_collab_dev.entity.ManagerInfo;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.BadgeType;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.enumType.RoleType;
import com.team3.api_collab_dev.mapper.UserMapper;
import com.team3.api_collab_dev.repository.ProfilRepo;
import com.team3.api_collab_dev.repository.ProjectRepo;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private ProfilRepo profilRepo;
    private ManagerInfoService managerInfoService;
    private FileStorageService fileStorageService;
    private ProjectRepo projectRepo;


    public String createUser(UserCreateDTO userDto) {
        Optional<User> optionalUser = this.userRepo.findByEmail(userDto.email());

        if (optionalUser.isPresent())
            throw new ExistSameEmailException("Un Compte existe déjà avec ce email ! :) Merci de revoir votre email.");

        User user = new User();
        user.setPseudo(userDto.speudo());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole(RoleType.USER);
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


    public Iterable<User> getAllUsers() {
        return this.userRepo.findAll();
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

        if (Objects.equals(dto.newPassword(), dto.confirmPassword())) {
            user.setPassword(passwordEncoder.encode(dto.newPassword()));

            userRepo.save(user);
            return " :) Votre mot de passe à été  modifier avec success ";
        } else {
            return " :) Les mots de passe ne sont pas identiques  ";
        }

    }


    public User getUserById(Long userId) {
        return this.userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException(" :) Oooops aucun utilisateur trouver avec l'id " + userId));
    }

    public String joinProjectAsManager(
            Long userId,
            Long projectId,
            ProfilType profilName,
            MultipartFile file,
            String githubLink) throws IOException {

        if (profilName == ProfilType.MANAGER) {

            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User non trouvé avec l'Id : " + userId));

            Project project = projectRepo.findById(projectId)
                    .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'Id : " + projectId));

            Optional<Profil> profilOptional = profilRepo.findByUserIdAndProfilName(userId, profilName);

            Profil profil;
            if (profilOptional.isPresent()) {
                profil = profilOptional.get();


            } else {
                profil = new Profil();
                profil.setUser(user);
                profil.setProfilName(profilName);
                profil.setLevel(Level.BEGINNER);
                profil.setBadge(BadgeType.RED);
                profil.setCoins(50);
                profil.setValidatedProjects(0);
                profil = profilRepo.save(profil);
            }

            if (!project.getPendingProfiles().contains(profil)) {
                ManagerInfo managerInfo = new ManagerInfo();
                managerInfo.setManager(profil);
                managerInfo.setGithubLink(githubLink);
                project.getPendingProfiles().add(profil);
                log.info(project.getPendingProfiles().toString());
                saveManagerDetail(file, managerInfo);
                projectRepo.save(project);
                return "Vous avez été ajouter à listre d'attente du Projet" + project.getTitle();

            }
            return "Vous êtes déjà sur la liste d'attenete de projet " + project.getTitle();

        } else return "Vous ne pouvez Joindre seulement joindre ce projet  que en tant que Manager :) Merci ";

    }


    // add this members to contribution request  with his profil
    public String joinProjectWithProfilName(
            Long projectId, Long userId,
            ProfilType profilName) throws IOException {

        if (profilName == ProfilType.MANAGER) {

            return "Vous ne pouvez pas joindre ce projet en tant Manager :) Merci ";

        } else {
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User non trouvé avec l'Id : " + userId));

            Project project = projectRepo.findById(projectId)
                    .orElseThrow(() -> new EntityNotFoundException("Projet non trouvé avec l'Id : " + projectId));

            if (project.getManager() == null) {
                return "Vous ne pouvez a joindre cd projet en tant que Designer | Developeur car il na pas de Gestionnaire :) Merci ";
            }

            Optional<Profil> profilOptional = profilRepo.findByUserIdAndProfilName(userId, profilName);

            Profil profil;
            if (profilOptional.isPresent()) {
                profil = profilOptional.get();

            } else {
                profil = new Profil();
                profil.setUser(user);
                profil.setProfilName(profilName);
                profil.setLevel(Level.BEGINNER);
                profil.setBadge(BadgeType.RED);
                profil.setCoins(50);
                profil.setValidatedProjects(0);
                profil = profilRepo.save(profil);
            }

            if (profil.getLevel() == project.getLevel() && profil.getCoins() >= project.getCoins()) {

                if (!project.getPendingProfiles().contains(profil)) {
                    project.getPendingProfiles().add(profil);
                    log.info(project.getPendingProfiles().toString());
                    projectRepo.save(project);
                }
                return "Demande envoyer avec  succès pour le  projet" + project.getTitle();

            } else {
                List<String> response = new ArrayList<>();
                //  response.add("Vous n'avez pas remplis les conditions d'adhésion pour ce projet :) Pas le niveau requis ou pas de piéce requis");
                return "Vous n'avez pas remplis les conditions d'adhésion pour ce projet :) Pas le niveau requis ou pas de piéce requis";
                //  return response;
            }
        }

    }


    public String saveManagerDetail(
            MultipartFile file,
            ManagerInfo managerInfo) throws IOException {

        if (file != null && !file.isEmpty()) {
            // utiliser file.getOriginalFilename()
            String cvPath = this.fileStorageService.storeFile(file);
            managerInfo.setPathCv(cvPath);
            this.managerInfoService.saveManager(managerInfo);
            return "Info du Manager" + managerInfo.getManager().getUser().getPseudo() + "Sauvegarder avec Succes ";
        } else return "Merci de chager un votre Cv ";


    }
}