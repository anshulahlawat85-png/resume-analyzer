package com.anshul.resumeanalyzer.controller;

import com.anshul.resumeanalyzer.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/test-email")
    public String testEmail() {

        emailService.sendEmail(
                "anshulahlawat85@gmail.com",
                "Resume Analyzer Test",
                "Congratulations! 🎉\n\nYour email service is working successfully."
        );

        return "Email Sent Successfully!";
    }
}
