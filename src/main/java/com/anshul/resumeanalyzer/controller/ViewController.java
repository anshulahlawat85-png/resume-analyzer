package com.anshul.resumeanalyzer.controller;

import com.anshul.resumeanalyzer.model.Resume;
import com.anshul.resumeanalyzer.repository.ResumeRepository;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.anshul.resumeanalyzer.model.ATSResult;
import com.anshul.resumeanalyzer.service.ATSService;
import com.anshul.resumeanalyzer.model.AnalysisHistory;
import com.anshul.resumeanalyzer.repository.AnalysisHistoryRepository;
import com.anshul.resumeanalyzer.service.PDFReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import com.anshul.resumeanalyzer.model.User;
import com.anshul.resumeanalyzer.repository.UserRepository;
import java.security.Principal;


@Controller
public class ViewController {

    private final ResumeRepository resumeRepository;
    private final ATSService atsService;
    private final AnalysisHistoryRepository historyRepository;
    private final PDFReportService pdfReportService;
    private final UserRepository userRepository;

    private ATSResult latestResult;

    public ViewController(
            ResumeRepository resumeRepository,
            ATSService atsService,
            AnalysisHistoryRepository historyRepository,
            PDFReportService pdfReportService,
            UserRepository userRepository) {

        this.resumeRepository = resumeRepository;
        this.atsService = atsService;
        this.historyRepository = historyRepository;
        this.pdfReportService = pdfReportService;
        this.userRepository = userRepository;
    }

    // Show all resumes
    @GetMapping("/resumes")
    public String showResumes(Model model) {
        model.addAttribute("resumes", resumeRepository.findAll());
        return "resumes";
    }



    // Show Add Resume Form
    @GetMapping("/add-resume")
    public String showAddResumeForm() {
        return "add-resume";
    }

    // Save Resume
    @PostMapping("/save-resume")
    public String saveResume(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String skills,
            @RequestParam int atsScore) {

        Resume resume = new Resume();

        resume.setName(name);
        resume.setEmail(email);
        resume.setSkills(skills);
        resume.setAtsScore(atsScore);

        resumeRepository.save(resume);

        return "redirect:/resumes";
    }

    // Delete Resume
    @GetMapping("/delete/{id}")
    public String deleteResume(@PathVariable Long id) {

        resumeRepository.deleteById(id);

        return "redirect:/resumes";
    }

    // Show Edit Form
    @GetMapping("/edit/{id}")
    public String editResume(@PathVariable Long id, Model model) {

        Resume resume = resumeRepository.findById(id).orElse(null);

        model.addAttribute("resume", resume);

        return "edit-resume";
    }

    // Update Resume
    @PostMapping("/update-resume")
    public String updateResume(
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String skills,
            @RequestParam int atsScore) {

        Resume resume = resumeRepository.findById(id).orElse(null);

        if (resume != null) {
            resume.setName(name);
            resume.setEmail(email);
            resume.setSkills(skills);
            resume.setAtsScore(atsScore);

            resumeRepository.save(resume);
        }

        return "redirect:/resumes";
    }

    // Search Resume
    @GetMapping("/search")
    public String searchResume(
            @RequestParam String keyword,
            Model model) {

        model.addAttribute(
                "resumes",
                resumeRepository.findByNameContainingIgnoreCase(keyword)
        );

        return "resumes";
    }

    // Upload Page
    @GetMapping("/upload")
    public String showUploadPage() {
        return "upload-resume";
    }

    @PostMapping("/upload-resume")

    public String uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobDescription") String jobDescription,
            Model model,
            jakarta.servlet.http.HttpSession session,
            Principal principal)
            throws IOException {
        // Apply limit only for guest users
        if (principal == null) {

            Integer count = (Integer) session.getAttribute("guestCount");

            if (count == null) {
                count = 0;
            }

            if (count >= 3) {
                return "free-limit";
            }

            session.setAttribute("guestCount", count + 1);
        }

        PDDocument document = Loader.loadPDF(file.getBytes());

        PDFTextStripper stripper = new PDFTextStripper();

        String resumeText = stripper.getText(document);

        document.close();

        ATSResult result =
                atsService.calculateATS(
                        resumeText,
                        jobDescription);
        latestResult = result;
        AnalysisHistory history = new AnalysisHistory();
        if (principal != null) {

            User user = userRepository
                    .findByEmail(principal.getName())
                    .orElse(null);

            history.setUser(user);
        }

        history.setFileName(
                file.getOriginalFilename());

        history.setAtsScore(
                result.getScore());

        history.setAnalyzedAt(
                java.time.LocalDateTime.now());

        historyRepository.save(history);

        model.addAttribute("result", result);

        return "analysis-result";
    }
    @GetMapping("/history")
    public String showHistory(
            Model model,
            Principal principal) {

        User user = userRepository
                .findByEmail(principal.getName())
                .orElse(null);

        var historyList = historyRepository.findByUser(user);

        model.addAttribute("historyList", historyList);

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
        AnalysisHistory topResume = historyList.stream()
                .max((a, b) ->
                        Integer.compare(
                                a.getAtsScore(),
                                b.getAtsScore()))
                .orElse(null);

        model.addAttribute(
                "topResume",
                topResume);

        double averageScore = historyList.stream()
                .mapToInt(AnalysisHistory::getAtsScore)
                .average()
                .orElse(0);

        model.addAttribute(
                "averageScore",
                Math.round(averageScore));

        // CHART CODE YAHAN SE START HOGA

        List<Long> labels = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();

        for (AnalysisHistory h : historyList) {

            labels.add(h.getId());

            scores.add(h.getAtsScore());
        }

        model.addAttribute(
                "chartLabels",
                labels);

        model.addAttribute(
                "chartScores",
                scores);

        return "history";
    }
    @GetMapping("/search-history")
    public String searchHistory(
            @RequestParam String keyword,
            Model model,
            Principal principal) {

        User user = userRepository
                .findByEmail(principal.getName())
                .orElse(null);

        var historyList =
                historyRepository
                        .findByUserAndFileNameContainingIgnoreCase(
                                user,
                                keyword
                        );
        model.addAttribute(
                "historyList",
                historyList);

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

        double averageScore = historyList.stream()
                .mapToInt(AnalysisHistory::getAtsScore)
                .average()
                .orElse(0);

        model.addAttribute(
                "averageScore",
                Math.round(averageScore));

        AnalysisHistory topResume = historyList.stream()
                .max((a, b) ->
                        Integer.compare(
                                a.getAtsScore(),
                                b.getAtsScore()))
                .orElse(null);

        model.addAttribute(
                "topResume",
                topResume);

        List<Long> labels = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();

        for (AnalysisHistory h : historyList) {

            labels.add(h.getId());

            scores.add(h.getAtsScore());
        }

        model.addAttribute(
                "chartLabels",
                labels);

        model.addAttribute(
                "chartScores",
                scores);

        return "history";
    }
    @GetMapping("/delete-history/{id}")
    public String deleteHistory(
            @PathVariable Long id) {

        historyRepository.deleteById(id);

        return "redirect:/history";
    }

    @GetMapping("/download-report")
    public ResponseEntity<byte[]> downloadReport() {

        ATSResult result = latestResult;

        byte[] pdf =
                pdfReportService.generateReport(result);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=ATS_Report.pdf")
                .contentType(
                        MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    @GetMapping("/users")
    public String showUsers(Model model) {

        model.addAttribute(
                "users",
                userRepository.findAll()
        );

        return "users";
    }
}
//Fresh deploy
//end