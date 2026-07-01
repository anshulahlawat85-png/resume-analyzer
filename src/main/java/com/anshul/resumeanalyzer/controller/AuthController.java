package com.anshul.resumeanalyzer.controller;

import com.anshul.resumeanalyzer.model.User;
import com.anshul.resumeanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user) {

        System.out.println("===== REGISTER REQUEST =====");
        System.out.println("Name : " + user.getFullName());
        System.out.println("Email: " + user.getEmail());

        boolean exists = userService.emailExists(user.getEmail());
        System.out.println("Email Exists? " + exists);

        if (exists) {
            System.out.println("Returning to Register Page");
            return "redirect:/register";
        }

        userService.registerUser(user);

        System.out.println("User Saved Successfully");
        System.out.println("Redirecting to Login");

        return "redirect:/login";
    }

}