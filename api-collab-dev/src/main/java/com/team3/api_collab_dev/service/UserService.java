package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.dto.ChangePasswordDTO;
import com.team3.api_collab_dev.dto.UserCreateDTO;
import com.team3.api_collab_dev.dto.UserUpdateDTO;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.mapper.UserMapper;
import com.team3.api_collab_dev.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@AllArgsConstructor
@Service
public class UserService {

    private UserRepo userRepo;
    //private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;


    public  User createUser(UserCreateDTO userDto){
        User user = new User();
        user.setPseudo(userDto.speudo());
        user.setEmail(userDto.email());
        //user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole(userDto.role());
        return  this.userRepo.save(user);
    }

    public String updateUserInfo(Long userId, UserUpdateDTO userInfo) {
        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Pas de user avec ID: " + userId));

        userMapper.updateEntity(existingUser, userInfo);

        userRepo.save(existingUser);

        return "Information mise à jour avec succès";
    }


    public String changePassword(Long userId, ChangePasswordDTO dto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User non trouver"));

        /*if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Mot de passe incorrect");
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));*/

        userRepo.save(user);
        return  "Mot de passe modifier avec success ";
    }

    public Iterable<User> getAllUsers(){
        return  this.userRepo.findAll();
    }



    public User login(String email, String password) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

       /* if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }*/

        return user;
    }




}
