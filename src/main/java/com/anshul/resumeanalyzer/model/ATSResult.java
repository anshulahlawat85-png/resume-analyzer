package com.anshul.resumeanalyzer.model;

public class ATSResult {

    private int score;
    private String matchedSkills;
    private String missingSkills;
    private String suggestions;

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
}