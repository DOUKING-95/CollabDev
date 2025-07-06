package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.entity.PointsTransaction;
import com.team3.api_collab_dev.service.PointAttributeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "pointsAttribution")
public class PointsAttributesController {
    private final PointAttributeService pointAttributeService;

    public PointsAttributesController(PointAttributeService pointAttributeService) {
        this.pointAttributeService = pointAttributeService;
    }

    public ResponseEntity<Void> assignPoints(@RequestParam Long contributionId, @RequestParam int points) {
        pointAttributeService.assignPoints(contributionId, points);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<Integer> getPointsBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(pointAttributeService.getPointsBalance(userId));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<PointsTransaction>> getPointsHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(pointAttributeService.getPointsHistory(userId));
    }
}
