package com.anshul.resumeanalyzer.controller;

import com.anshul.resumeanalyzer.model.Resume;
import com.anshul.resumeanalyzer.repository.ResumeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeRepository resumeRepository;

    public ResumeController(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @PostMapping
    public Resume saveResume(@RequestBody Resume resume) {
        return resumeRepository.save(resume);
    }

    @GetMapping
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    @GetMapping("/addtest")
    public Resume addTestResume() {

        Resume resume = new Resume();

        resume.setName("Anshul");
        resume.setEmail("anshul@gmail.com");
        resume.setSkills("Java, Spring Boot");
        resume.setAtsScore(85);

        return resumeRepository.save(resume);
    }
}