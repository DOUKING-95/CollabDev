package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.ManagerInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerInfoRepo extends CrudRepository<ManagerInfo, Long> {
    Optional<ManagerInfo> findByGithubLink(String githubLink);
}
