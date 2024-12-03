package com.azure.auth.service.controller;

import com.azure.auth.service.configuration.EmailWithAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class EmailAttachmentController {

    @Autowired
    private EmailWithAttachment emailWithAttachment;

    @GetMapping(path = "/mail/attach")
    public String sendEmail() {
        String attach = "https://bucket-ec2-1.s3.eu-north-1.amazonaws.com/rob-martin-DWKS4N-zW2I-unsplash.jpg";
        return emailWithAttachment.sendEmailWithDynamicImageAttachment("ajayshinde8867@gmail.com", "From From", "ASJLASKAHSKAHSJK", attach);
    }
}
