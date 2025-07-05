package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepo extends JpaRepository<Badge,Long> {
}
