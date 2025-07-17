package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.enumType.RoleType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilRepo  extends CrudRepository<Profil, Long> {

    Optional<Profil> findByUserIdAndProfilName(Long userId, ProfilType roleType);


}
