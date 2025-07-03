package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.service.EtatProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class EtatPojetController {

    @Autowired
    private EtatProjetService projetService;

    // ADMIN : après affectation d'un gestionnaire
    @PostMapping("/{projetId}/set-en-cours")
    public Project setProjetEnCours(@PathVariable Long projetId) {
        return projetService.changerEtatEnCours(projetId);
    }

    // GESTIONNAIRE : soumettre pour validation
    @PostMapping("/{projetId}/submit")
    public Project submitProjet(@PathVariable Long projetId) {
        return projetService.soumettrePourValidation(projetId);
    }

    // ADMIN : valider ou rejeter
    @PostMapping("/{projetId}/decision")
    public Project decideProjet(@PathVariable Long projetId, @RequestParam boolean accepte) {
        return projetService.validerOuRejeter(projetId, accepte);
    }
}
