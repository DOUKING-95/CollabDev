package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.LivrableRequestDTO;
import com.team3.api_collab_dev.dto.LivrableResponseDTO;
import com.team3.api_collab_dev.entity.Livrable;
import com.team3.api_collab_dev.service.LivrableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livrables")
@RequiredArgsConstructor
public class LivrableController {

    private final LivrableService livrableService;

    // Gestionnaire crée un livrable (status EN_COURS)
    @PostMapping("/creer")
    public ResponseEntity<LivrableResponseDTO> creerLivrable(@RequestBody LivrableRequestDTO dto) {
        LivrableResponseDTO response = livrableService.creerLivrable(dto);
        return ResponseEntity.ok(response);
    }
    // Contributeur soumet le livrable (status TERMINE)
    @PutMapping("/{id}/soumettre")
    public ResponseEntity<LivrableResponseDTO> soumettreLivrable(@PathVariable Long id) {
        LivrableResponseDTO response = livrableService.soumettreLivrable(id);
        return ResponseEntity.ok(response);
    }


    // Liste livrables d’un gestionnaire
    @GetMapping("/gestionnaire/{id}")
    public ResponseEntity<List<LivrableResponseDTO>> getLivrablesPourGestionnaire(@PathVariable Long id) {
        List<LivrableResponseDTO> livrables = livrableService.getLivrablesPourGestionnaire(id);
        return ResponseEntity.ok(livrables);
    }

}
