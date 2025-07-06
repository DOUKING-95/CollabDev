package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Contributions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionRepository extends JpaRepository<Contributions , Long> {
}
