package com.anshul.resumeanalyzer.repository;

import com.anshul.resumeanalyzer.model.AnalysisHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalysisHistoryRepository
        extends JpaRepository<AnalysisHistory, Long> {

    List<AnalysisHistory>
    findByFileNameContainingIgnoreCase(String keyword);

}