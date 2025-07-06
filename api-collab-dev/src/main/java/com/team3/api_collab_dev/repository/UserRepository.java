package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
