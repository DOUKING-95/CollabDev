package com.team3.api_collab_dev.controller;


import com.team3.api_collab_dev.dto.LivrableRequestDTO;
import com.team3.api_collab_dev.dto.LivrableResponseDTO;
import com.team3.api_collab_dev.entity.Livrable;
import com.team3.api_collab_dev.service.LivrableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/livrables")
@RequiredArgsConstructor
public class LivrableController {

    private final LivrableService service;

    @PostMapping("/soumettre")
    public ResponseEntity<?> soumettre(@RequestBody LivrableRequestDTO dto) {
        Livrable livrable = service.soumettreLivrable(dto);
        return ResponseEntity.ok(Map.of(
                "message", "Livrable soumis avec succès",
                "livrableId", livrable.getId()
        ));
    }

    @GetMapping("/gestionnaire/{id}")
    public List<LivrableResponseDTO> getLivrablesPourGestionnaire(@PathVariable Long id) {
        return service.getLivrablesPourGestionnaire(id);
    }
}
