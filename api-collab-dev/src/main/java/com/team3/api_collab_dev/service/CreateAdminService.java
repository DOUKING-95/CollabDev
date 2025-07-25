package com.team3.api_collab_dev.service;


import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.RoleType;
import com.team3.api_collab_dev.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Utils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;

@Service
@AllArgsConstructor
@Slf4j
public class CreateAdminService implements CommandLineRunner {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
            User admin = new User();
            admin.setPseudo("admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(RoleType.ADMIN);

            userRepository.save(admin);

            log.info(" Admin créé avec succès ");
        } else {
            log.info(" Ooops :)  Admin existe déjà Merci ! ");

        }

        //----------------------------------------------------------------------

    }
}

