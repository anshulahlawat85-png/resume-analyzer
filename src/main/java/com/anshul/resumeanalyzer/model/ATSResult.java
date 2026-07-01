package com.anshul.resumeanalyzer.model;

public class ATSResult {

    private int score;
    private String matchedSkills;
    private String missingSkills;
    private String suggestions;
    private String resumeQuality;
    private String grade;
    private int matchPercentage;
    private String aiRecommendation;
    private String resumeSummary;
    private int technicalScore;
    private int projectScore;
    private int educationScore;
    private int experienceScore;
    private int formattingScore;
    private int contactScore;
    private String strengths;
    private String weaknesses;

    public ATSResult() {
    }

    public ATSResult(int score,
                     String matchedSkills,
                     String missingSkills,
                     String suggestions) {

        this.score = score;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.suggestions = suggestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(String matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public String getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(String missingSkills) {
        this.missingSkills = missingSkills;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }
    public String getResumeQuality() {
        return resumeQuality;
    }

    public void setResumeQuality(String resumeQuality) {
        this.resumeQuality = resumeQuality;
    }
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(int matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public String getAiRecommendation() {
        return aiRecommendation;
    }

    public void setAiRecommendation(String aiRecommendation) {
        this.aiRecommendation = aiRecommendation;
    }

    public String getResumeSummary() {
        return resumeSummary;
    }

    public void setResumeSummary(String resumeSummary) {
        this.resumeSummary = resumeSummary;
    }
    public int getTechnicalScore() {
        return technicalScore;
    }

    public void setTechnicalScore(int technicalScore) {
        this.technicalScore = technicalScore;
    }

    public int getProjectScore() {
        return projectScore;
    }

    public void setProjectScore(int projectScore) {
        this.projectScore = projectScore;
    }

    public int getEducationScore() {
        return educationScore;
    }

    public void setEducationScore(int educationScore) {
        this.educationScore = educationScore;
    }

    public int getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(int experienceScore) {
        this.experienceScore = experienceScore;
    }

    public int getFormattingScore() {
        return formattingScore;
    }

    public void setFormattingScore(int formattingScore) {
        this.formattingScore = formattingScore;
    }

    public int getContactScore() {
        return contactScore;
    }

    public void setContactScore(int contactScore) {
        this.contactScore = contactScore;
    }
    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(String weaknesses) {
        this.weaknesses = weaknesses;
    }
}