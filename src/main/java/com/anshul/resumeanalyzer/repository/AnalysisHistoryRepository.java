package com.anshul.resumeanalyzer.repository;

import com.anshul.resumeanalyzer.model.AnalysisHistory;
import com.anshul.resumeanalyzer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalysisHistoryRepository
        extends JpaRepository<AnalysisHistory, Long> {

    // Search by file name
    List<AnalysisHistory> findByFileNameContainingIgnoreCase(String keyword);

    // History of a specific user
    List<AnalysisHistory> findByUser(User user);

    // Search history of a specific user
    List<AnalysisHistory> findByUserAndFileNameContainingIgnoreCase(
            User user,
            String keyword
    );
}