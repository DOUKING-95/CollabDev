package com.team3.api_collab_dev.service;

import com.team3.api_collab_dev.entity.Badge;
import com.team3.api_collab_dev.entity.Profil;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.BadgeType;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.ProfilType;
import com.team3.api_collab_dev.repository.BadgeRepo;
import com.team3.api_collab_dev.repository.ProfilRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class BadgeService {

    private BadgeRepo badgeRepo;
    private UserService userService;
    private ProfilRepo profilRepo;




    public Badge createBadge(Badge badge){
        return  badgeRepo.save(badge);
    }

    public List<Badge> getAllBadge(){

        List<Badge> badges = new ArrayList<>();

        this.badgeRepo.findAll().forEach(badges::add);
        return badges;

    }
    @Scheduled(cron = "0 0 */12 * * *")
    public void assignedBadge(){


        for (Profil profil : profilRepo.findAll()) {

            if (profil.getProfilName().equals(ProfilType.DEVELOPER) && profil.getLevel().equals(Level.BEGINNER) && profil.getValidatedProjects() >= 10) {
                profil.setLevel((Level.INTERMEDIATE));
                profil.setBadge(BadgeType.YELLOW);
                profilRepo.save(profil);
                log.info( "Felicitations " + profil.getUser().getPseudo() + "vous avez à present le niveau" + Level.INTERMEDIATE);
            }

            else if (profil.getProfilName().equals(ProfilType.DEVELOPER) && profil.getLevel().equals(Level.INTERMEDIATE) && profil.getCoins() >= 30) {
                profil.setLevel((Level.ADVANCED));
                profil.setBadge(BadgeType.GREEN);
                profilRepo.save(profil);
                log.info("Felicitations " + profil.getUser().getPseudo() + "vous avez à present le niveau" + Level.ADVANCED);
            }

            else if (profil.getProfilName().equals(ProfilType.DESIGNER) && profil.getLevel().equals(Level.BEGINNER) && profil.getCoins() >= 10) {
                profil.setLevel((Level.INTERMEDIATE));
                profil.setBadge(BadgeType.YELLOW);
                profilRepo.save(profil);
                log.info("Felicitations " + profil.getUser().getPseudo() + "vous avez à present le niveau" + Level.INTERMEDIATE);
            }

            else if (profil.getProfilName().equals(ProfilType.DESIGNER) && profil.getLevel().equals(Level.INTERMEDIATE) && profil.getCoins() >= 30) {
                profil.setLevel((Level.ADVANCED));
                profil.setBadge(BadgeType.GREEN);
                profilRepo.save(profil);
                log.info("Felicitations " + profil.getUser().getPseudo() + "vous avez à present le niveau" + Level.ADVANCED);
            }

            else if (profil.getProfilName().equals(ProfilType.MANAGER) && profil.getLevel().equals(Level.BEGINNER) && profil.getCoins() >= 10) {
                profil.setLevel((Level.INTERMEDIATE));
                profil.setBadge(BadgeType.YELLOW);
                profilRepo.save(profil);
                log.info("Felicitations " + profil.getUser().getPseudo() + "vous avez à present le niveau" + Level.INTERMEDIATE);
            }

            else if (profil.getProfilName().equals(ProfilType.MANAGER) && profil.getLevel().equals(Level.INTERMEDIATE) && profil.getCoins() >= 30) {
                profil.setLevel((Level.ADVANCED));
                profil.setBadge(BadgeType.GREEN);
                profilRepo.save(profil);
                log.info("Felicitations " + profil.getUser().getPseudo() + "vous avez à present le niveau" + Level.ADVANCED);
            }


        }




    }



}
