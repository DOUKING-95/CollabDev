package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.Contributions;
import com.team3.api_collab_dev.entity.PointsTransaction;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.ContributionsStatusType;
import com.team3.api_collab_dev.repository.ContributionRepository;
import com.team3.api_collab_dev.repository.PointsTransactionRepository;
import com.team3.api_collab_dev.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PointAttributeService {
   private final ContributionRepository contributionRepository;
   private final PointsTransactionRepository pointTransactionRepository;
   private final UserRepository userRepository;

   public PointAttributeService(ContributionRepository contributionRepository ,
                         PointsTransactionRepository pointsTransactionRepository,
                         UserRepository userRepository){
       this.userRepository = userRepository;
       this.pointTransactionRepository = pointsTransactionRepository;
       this.contributionRepository = contributionRepository;
   }
   @Transactional
    public void assignPoints(Long contributionId , int points){
       Contributions contribution = contributionRepository.findById(contributionId).orElseThrow(
               () -> new IllegalArgumentException("Contribution non trouver")
       );
       if (contribution.getStatusType() != ContributionsStatusType.ACCEPTED){
           throw new IllegalStateException("La contribution doit être approuvée pour attribuer des points");
       }
       User user = contribution.getUser();
       user.setPointsBalance(user.getPointsBalance() + points);
       userRepository.save(user);

       PointsTransaction transaction = new PointsTransaction();
       transaction.setUser(user);
       transaction.setPoints(points);
       transaction.setDescription("Points attribués pour la contribution :" + contributionId);
       transaction.setCreatedAt(LocalDateTime.now());
       pointTransactionRepository.save(transaction);
   }
    public int getPointsBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User non trouver"));
        return user.getPointsBalance();
    }

    public List<PointsTransaction> getPointsHistory(Long userId) {
        return pointTransactionRepository.findByUserId(userId);
    }


}
