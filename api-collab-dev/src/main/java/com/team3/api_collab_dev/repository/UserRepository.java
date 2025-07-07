package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Trouver un utilisateur par email
    Optional<User> findByEmail(String email);

    // Trouver un utilisateur par pseudo
    Optional<User> findBySpeudo(String speudo);

    // Vérifier si un email existe déjà
    boolean existsByEmail(String email);

    // Vérifier si un pseudo existe déjà
    boolean existsBySpeudo(String speudo);
}