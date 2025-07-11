package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.WebPushRequest;
 //import com.team3.api_collab_dev.service.WebPushService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
@RestController
@RequestMapping("/webpush")
public class WebPushController {

    private final WebPushService webPushService;

    public WebPushController(WebPushService webPushService) {
        this.webPushService = webPushService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendWebPush(@RequestBody WebPushRequest request) {
        try {
            webPushService.sendPushNotification(
                    request.getEndpoint(),
                    request.getPublicKey(),
                    request.getAuth(),
                    request.getPayload()
            );
            return ResponseEntity.ok("Notification envoyée");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur d’envoi : " + e.getMessage());
        }
    }
}
 */

