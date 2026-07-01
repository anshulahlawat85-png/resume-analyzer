package com.anshul.resumeanalyzer.controller;

import com.anshul.resumeanalyzer.repository.UserRepository;
import com.anshul.resumeanalyzer.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.anshul.resumeanalyzer.service.UserService;

import java.util.Random;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    // Temporary OTP Storage
    private String generatedOtp;
    private String userEmail;

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/verify-otp")
    public String verifyOtpPage() {

        return "verify-otp";
    }

    @PostMapping("/forgot-password")
    public String sendOtp(@RequestParam String email,
                          Model model) {

        System.out.println("===== FORGOT PASSWORD CALLED =====");
        System.out.println("Email Received: " + email);

        // Check if email exists
        if (userRepository.findByEmail(email).isEmpty()) {

            model.addAttribute("error",
                    "Email not registered!");

            return "forgot-password";
        }

        // Generate 6-digit OTP
        generatedOtp = String.valueOf(
                100000 + new Random().nextInt(900000));

        userEmail = email;

        // Send OTP Email
        emailService.sendEmail(
                email,
                "Resume Analyzer Password Reset OTP",
                "Your OTP is: " + generatedOtp
        );

        // Print OTP in console (for testing)
        System.out.println("Generated OTP : " + generatedOtp);

        // Next step
        return "redirect:/verify-otp";
    }
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp,
                            Model model) {

        if (generatedOtp == null || !generatedOtp.equals(otp)) {

            model.addAttribute("error",
                    "Invalid OTP!");

            return "verify-otp";
        }

        return "redirect:/reset-password";
    }
    @GetMapping("/reset-password")
    public String resetPasswordPage() {

        return "reset-password";
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String password) {

        userService.updatePassword(userEmail, password);

        generatedOtp = null;
        userEmail = null;

        return "redirect:/login";
    }

}
