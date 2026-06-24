package com.anshul.resumeanalyzer.model;

import jakarta.persistence.*;

@Entity
public class AnalysisHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private int atsScore;

    @Column(nullable = false)
    private java.time.LocalDateTime analyzedAt;

    public AnalysisHistory() {
    }

    @PrePersist
    public void prePersist() {
        analyzedAt = java.time.LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getAtsScore() {
        return atsScore;
    }

    public void setAtsScore(int atsScore) {
        this.atsScore = atsScore;
    }

    public java.time.LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }

    public void setAnalyzedAt(java.time.LocalDateTime analyzedAt) {
        this.analyzedAt = analyzedAt;
    }
}