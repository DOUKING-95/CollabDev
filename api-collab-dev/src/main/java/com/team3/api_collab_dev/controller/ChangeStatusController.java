package com.team3.api_collab_dev.controller;


import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.service.ChangeStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
@RequiredArgsConstructor
public class ChangeStatusController {
    private final ChangeStatusService service;

    @PutMapping("/{id}/soumettre")
    public ResponseEntity<String> soumettreProjet(@PathVariable Long id) {
        service.soumettreProjetPourValidation(id);
        return ResponseEntity.ok("Projet soumis à l'admin.");
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<String> validerProjet(
            @PathVariable Long id,
            @RequestParam("decision") String decision) {

        service.validerProjet(id, decision);
        return ResponseEntity.ok("Décision prise : " + decision);
    }

    @GetMapping("/admin/a-examiner/{adminId}")
    public List<Project> projetsAExaminer(@PathVariable Long adminId) {
        return service.getProjetsParStatus(adminId, Status.EN_COURS_DE_VALIDATION);
    }

    @GetMapping("/admin/acceptes/{adminId}")
    public List<Project> projetsAcceptes(@PathVariable Long adminId) {
        return service.getProjetsParStatus(adminId, Status.ACCEPTE);
    }

    @GetMapping("/admin/refuses/{adminId}")
    public List<Project> projetsRefuses(@PathVariable Long adminId) {
        return service.getProjetsParStatus(adminId, Status.REFUSE);
    }
}
