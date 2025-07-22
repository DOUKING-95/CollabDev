package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.ApiReponse;
import com.team3.api_collab_dev.entity.Badge;
import com.team3.api_collab_dev.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "badges")
@Tag(name = "Badge", description = "Manage badge")
public class BadgeController {

    private BadgeService badgeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED )
    @Operation(summary = "Create a new badge")
    @ApiResponse(responseCode = "201", description = "badge created successfully")
    public ResponseEntity<ApiReponse<?>> createBadge(@RequestBody Badge badge){
        return  ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.CREATED.value()),
                        HttpStatus.CREATED.getReasonPhrase(),
                        this.badgeService.createBadge(badge)
                )
        );
    }


}
