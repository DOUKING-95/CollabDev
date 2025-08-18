package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.repository.ProfilRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfilService {

    private ProfilRepo profilRepo;

    public double getDesignerCoinsByUser(Long userId) {
        // Récupérer le profil Designer de l'utilisateur
        Profil designerProfil = profilRepo.findByUserIdAndProfilName(userId, ProfilType.DESIGNER)
                .orElseThrow(() -> new EntityNotFoundException("Profil Designer introuvable pour l'utilisateur " + userId));

        return designerProfil.getCoins();
    }

    public double getDeveloperCoinsByUser(Long userId) {
        // Récupérer le profil Developer de l'utilisateur
        Profil developerProfil = profilRepo.findByUserIdAndProfilName(userId, ProfilType.DEVELOPER)
                .orElseThrow(() -> new EntityNotFoundException("Profil Developper introuvable pour l'utilisateur " + userId));

        return developerProfil.getCoins();
    }

    public double getManagerCoinsByUser(Long userId) {
        // Récupérer le profil Manager de l'utilisateur
        Profil managerProfil = profilRepo.findByUserIdAndProfilName(userId, ProfilType.MANAGER)
                .orElseThrow(() -> new EntityNotFoundException("Profil Manager introuvable pour l'utilisateur " + userId));

        return managerProfil.getCoins();
    }

    public double getTotalCoinsByUser(Long userId) {
        double designerCoins = 0;
        double developerCoins = 0;
        double managerCoins = 0;

        // Designer
        Optional<Profil> designer = profilRepo.findByUserIdAndProfilName(userId, ProfilType.DESIGNER);
        if (designer.isPresent()) designerCoins = designer.get().getCoins();

        // Developer
        Optional<Profil> developer = profilRepo.findByUserIdAndProfilName(userId, ProfilType.DEVELOPER);
        if (developer.isPresent()) developerCoins = developer.get().getCoins();

        // Manager
        Optional<Profil> manager = profilRepo.findByUserIdAndProfilName(userId, ProfilType.MANAGER);
        if (manager.isPresent()) managerCoins = manager.get().getCoins();

        return designerCoins + developerCoins + managerCoins;
    }








}
