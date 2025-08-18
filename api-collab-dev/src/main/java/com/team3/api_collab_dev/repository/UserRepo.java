package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);



}
