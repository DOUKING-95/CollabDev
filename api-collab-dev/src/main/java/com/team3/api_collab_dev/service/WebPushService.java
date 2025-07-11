package com.team3.api_collab_dev.service;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
@Service
public class WebPushService {

    @Value("${vapid.public.key}")
    private String publicKey;

    @Value("${vapid.private.key}")
    private String privateKey;

    @Value("${vapid.subject}")
    private String subject;

    public void sendPushNotification(String endpoint, String userPublicKey, String userAuth, String payload) throws Exception {
        PushService pushService = new PushService();
        pushService.setPublicKey(Utils.loadPublicKey(publicKey));
        pushService.setPrivateKey(Utils.loadPrivateKey(privateKey));
        pushService.setSubject(subject);

        Notification notification = new Notification(endpoint, userPublicKey, userAuth, payload);
        pushService.send(notification);
    }
}
*/
