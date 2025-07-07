package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Livrable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivrableRepository extends JpaRepository<Livrable,Long> {
    List<Livrable> findByProjet_Gestionnaire_Id(Long gestionnaireId);
}
