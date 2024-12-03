package com.azure.auth.service.controller;

import com.azure.auth.service.configuration.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping(path = "/mail")
    public String sendEmail() {
        return emailService.sendEmail("ajay.s@3ktechnologies.com", "Hii", "Hiii");
    }
}
