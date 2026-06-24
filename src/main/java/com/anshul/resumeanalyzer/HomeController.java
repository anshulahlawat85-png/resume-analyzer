package com.anshul.resumeanalyzer;

import com.anshul.resumeanalyzer.model.AnalysisHistory;
import com.anshul.resumeanalyzer.repository.AnalysisHistoryRepository;
import com.anshul.resumeanalyzer.repository.ResumeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ResumeRepository resumeRepository;
    private final AnalysisHistoryRepository historyRepository;

    public HomeController(
            ResumeRepository resumeRepository,
            AnalysisHistoryRepository historyRepository) {

        this.resumeRepository = resumeRepository;
        this.historyRepository = historyRepository;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "totalResumes",
                resumeRepository.count());

        var historyList =
                historyRepository.findAll();

        model.addAttribute(
                "totalAnalyses",
                historyList.size());

        int highestScore = historyList.stream()
                .mapToInt(AnalysisHistory::getAtsScore)
                .max()
                .orElse(0);

        model.addAttribute(
                "highestScore",
                highestScore);

        model.addAttribute(
                "recentActivities",
                historyList.stream()
                        .filter(h -> h.getAnalyzedAt() != null)
                        .sorted((a,b) ->
                                b.getAnalyzedAt()
                                        .compareTo(a.getAnalyzedAt()))
                        .limit(5)
                        .toList());

        return "home";
    }
}