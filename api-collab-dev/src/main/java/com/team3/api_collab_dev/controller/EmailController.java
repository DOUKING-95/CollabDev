package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.EmailDto;
import com.team3.api_collab_dev.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody EmailDto emailDto) {
        emailService.sendEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getText());
        return "Email envoyer avec succes !";
    }
}
