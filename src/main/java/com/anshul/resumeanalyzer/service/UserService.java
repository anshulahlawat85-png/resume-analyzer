package com.anshul.resumeanalyzer.service;

import com.anshul.resumeanalyzer.model.User;
import com.anshul.resumeanalyzer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Normal Register
    public void registerUser(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        userRepository.save(user);
    }

    // Google Register
    public void saveGoogleUser(User user) {

        userRepository.save(user);
    }

    public boolean emailExists(String email) {

        return userRepository.findByEmail(email).isPresent();
    }
    public void updatePassword(String email, String newPassword) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {

            user.setPassword(passwordEncoder.encode(newPassword));

            userRepository.save(user);
        }
    }
}
