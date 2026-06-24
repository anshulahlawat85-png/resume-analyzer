package com.anshul.resumeanalyzer.repository;

import com.anshul.resumeanalyzer.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByNameContainingIgnoreCase(String name);

}