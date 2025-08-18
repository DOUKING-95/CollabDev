package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.service.ProfilService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profil")
@RequiredArgsConstructor
public class ProfilController {

    private final ProfilService profilService;

    @GetMapping(path = "/{userId}/managerCoins")
    public ResponseEntity<ApiReponse<Double>> getManagerCoins(@PathVariable(name = "userId") Long userId) {
        double coins = profilService.getManagerCoinsByUser(userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        coins
                )
        );
    }

    @GetMapping(path = "/{userId}/designerCoins")
    public ResponseEntity<ApiReponse<Double>> getDesignerCoins(@PathVariable(name = "userId") Long userId) {
        double coins = profilService.getDesignerCoinsByUser(userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        coins
                )
        );
    }

    @GetMapping(path = "/{userId}/developerCoins")
    public ResponseEntity<ApiReponse<Double>> getDeveloperCoins(@PathVariable(name = "userId") Long userId) {
        double coins = profilService.getDeveloperCoinsByUser(userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        coins
                )
        );
    }

    @GetMapping(path = "/{userId}/totalCoins")
    public ResponseEntity<ApiReponse<Double>> getTotalCoins(@PathVariable(name = "userId") Long userId) {
        double coins = profilService.getTotalCoinsByUser(userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        coins
                )
        );
    }



}
