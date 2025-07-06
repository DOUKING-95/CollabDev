package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.PointsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointsTransactionRepository extends JpaRepository<PointsTransaction, Long> {
    List<PointsTransaction> findByUserId(Long userId);
}
