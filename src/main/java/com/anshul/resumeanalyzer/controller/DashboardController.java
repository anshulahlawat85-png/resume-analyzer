package com.anshul.resumeanalyzer.controller;

import com.anshul.resumeanalyzer.model.AnalysisHistory;
import com.anshul.resumeanalyzer.model.User;
import com.anshul.resumeanalyzer.repository.AnalysisHistoryRepository;
import com.anshul.resumeanalyzer.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class DashboardController {

    private final UserRepository userRepository;
    private final AnalysisHistoryRepository historyRepository;

    public DashboardController(UserRepository userRepository,
                               AnalysisHistoryRepository historyRepository) {
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        System.out.println("Logged in Email: " + principal.getName());

        User user = userRepository
                .findByEmail(principal.getName())
                .orElse(null);

        System.out.println("User Found: " + user);

        // Temporary check to avoid NullPointerException
        if (user == null) {
            model.addAttribute("error",
                    "Google login successful, but this user does not exist in the database.");
            return "login";
        }

        List<AnalysisHistory> historyList =
                historyRepository.findByUser(user);

        int total = historyList.size();

        int highest = historyList.stream()
                .mapToInt(AnalysisHistory::getAtsScore)
                .max()
                .orElse(0);

        int average = (int) historyList.stream()
                .mapToInt(AnalysisHistory::getAtsScore)
                .average()
                .orElse(0);

        model.addAttribute("username", user.getFullName());
        model.addAttribute("email", user.getEmail());

        String loginType =
                "GOOGLE_LOGIN".equals(user.getPassword())
                        ? "Google"
                        : "Email";

        model.addAttribute("loginType", loginType);
        model.addAttribute("totalAnalyses", total);
        model.addAttribute("highestScore", highest);
        model.addAttribute("averageScore", average);

        AnalysisHistory latestResume = historyList.stream()
                .max((a, b) -> a.getAnalyzedAt().compareTo(b.getAnalyzedAt()))
                .orElse(null);

        String formattedDate = "";

        if (latestResume != null) {
            formattedDate = latestResume.getAnalyzedAt()
                    .format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
        }

        model.addAttribute("formattedDate", formattedDate);
        model.addAttribute("latestResume", latestResume);

        return "dashboard";
    }
}