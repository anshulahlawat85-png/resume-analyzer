package com.anshul.resumeanalyzer.service;

import com.anshul.resumeanalyzer.model.ATSResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ATSService {

    private static final List<String> KNOWN_SKILLS = Arrays.asList(
            "java",
            "spring boot",
            "mysql",
            "rest api",
            "hibernate",
            "jpa",
            "maven",
            "git",
            "html",
            "css",
            "javascript",
            "docker",
            "aws",
            "jenkins",
            "microservices",
            "thymeleaf",
            "github",
            "linux",
            "python",
            "c",
            "c++",
            "react",
            "angular",
            "node.js",
            "mongodb",
            "bootstrap",
            "oop",
            "data structures",
            "algorithms"
    );
    private static final Set<String> STOP_WORDS = Set.of(
            "the", "and", "or", "for", "with", "using",
            "to", "of", "in", "on", "a", "an",
            "is", "are", "be", "as", "at",
            "by", "from", "will", "must",
            "required", "requirements",
            "responsibilities", "experience",
            "knowledge", "ability",
            "looking",
            "developer",
            "develop",
            "developing",
            "build",
            "building",
            "write",
            "writing",
            "clean",
            "maintainable",
            "maintain",
            "participate",
            "reviews",
            "debug",
            "optimize",
            "performance",
            "deploy",
            "deployment",
            "collaborate",
            "teams",
            "team",
            "work",
            "working",
            "application",
            "applications",
            "company",
            "role",
            "position",
            "candidate",
            "candidates",
            "good",
            "strong"
    );
    private Set<String> extractTechnicalSkills(String jobDescription) {

        Set<String> technicalSkills = new LinkedHashSet<>();

        for (String skill : KNOWN_SKILLS) {

            if (jobDescription.toLowerCase().contains(skill.toLowerCase())) {

                technicalSkills.add(skill);

            }
        }

        return technicalSkills;
    }

    public ATSResult calculateATS(String resumeText,
                                  String jobDescription) {

        String resume = resumeText.toLowerCase();
        String jd = jobDescription.toLowerCase();

        List<String> requiredSkills = new ArrayList<>(
                extractTechnicalSkills(jobDescription)
        );

        requiredSkills = new ArrayList<>(new LinkedHashSet<>(requiredSkills));

        StringBuilder matchedSkills = new StringBuilder();
        StringBuilder missingSkills = new StringBuilder();
        StringBuilder suggestions = new StringBuilder();
        StringBuilder resumeQuality = new StringBuilder();
        StringBuilder strengths = new StringBuilder();
        StringBuilder weaknesses = new StringBuilder();

        int matched = 0;
        int technicalScore = 0;
        int projectScore = 0;
        int educationScore = 0;
        int experienceScore = 0;
        int formattingScore = 0;
        int contactScore = 0;

        if (resume.contains("@")) {

            strengths.append("✔ Email Address Present\n");

            resumeQuality.append("✅ Email Found\n");

        } else {

            weaknesses.append("❌ Email Address Missing\n");

            resumeQuality.append("❌ Email Missing\n");

        }

        if (resume.matches("(?s).*\\d{10}.*")) {

            strengths.append("✔ Phone Number Present\n");

            resumeQuality.append("✅ Phone Number Found\n");

        } else {

            weaknesses.append("❌ Phone Number Missing\n");

            resumeQuality.append("❌ Phone Number Missing\n");

        }
        if (resume.contains("@") &&
                resume.matches("(?s).*\\d{10}.*")) {

            contactScore = 5;

        }

        if (resume.contains("education")) {

            educationScore = 15;

            strengths.append("✔ Education Section Present\n");

            resumeQuality.append("✅ Education Section Found\n");

        } else {

            educationScore = 0;

            weaknesses.append("❌ Education Section Missing\n");

            resumeQuality.append("❌ Education Section Missing\n");

        }

        if (resume.contains("skills")) {
            resumeQuality.append("✅ Skills Section Found\n");
        } else {
            resumeQuality.append("❌ Skills Section Missing\n");
        }

        if (resume.contains("project")) {

            projectScore = 20;

            strengths.append("✔ Projects Section Present\n");

            resumeQuality.append("✅ Projects Section Found\n");

        } else {

            projectScore = 0;

            weaknesses.append("❌ Projects Section Missing\n");

            resumeQuality.append("❌ Projects Section Missing\n");

        }

        if (resume.contains("experience")) {

            experienceScore = 10;

            strengths.append("✔ Experience Section Present\n");

            resumeQuality.append("✅ Experience Section Found\n");

        } else {

            experienceScore = 0;

            weaknesses.append("❌ Experience Section Missing\n");

            resumeQuality.append("❌ Experience Section Missing\n");

        }

        if (resume.contains("linkedin")) {
            resumeQuality.append("✅ LinkedIn Found\n");
        } else {
            resumeQuality.append("❌ LinkedIn Missing\n");
        }

        if (resume.contains("github")) {
            resumeQuality.append("✅ GitHub Found\n");
        } else {
            resumeQuality.append("❌ GitHub Missing\n");
        }

        if (resume.contains("education")
                && resume.contains("skills")
                && resume.contains("project")) {

            formattingScore = 10;

        }

        // ---- Paste Part 2 below this line ----
        for (String skill : requiredSkills) {

            if (resume.contains(skill)) {

                matched++;

                matchedSkills
                        .append("✔ ")
                        .append(skill)
                        .append("\n");

            } else {

                missingSkills
                        .append("❌ ")
                        .append(skill)
                        .append("\n");

                suggestions
                        .append("• Learn ")
                        .append(skill)
                        .append("\n");

                weaknesses
                        .append("❌ Missing: ")
                        .append(skill)
                        .append("\n");
            }
        }

        int totalSkills = requiredSkills.size();

        int score = 0;

        if (totalSkills > 0) {
            score = (matched * 100) / totalSkills;
        }
        technicalScore = (matched * 40) / Math.max(totalSkills, 1);

        if (matchedSkills.length() == 0) {
            matchedSkills.append("No matching skills found.");
        }

        if (missingSkills.length() == 0) {
            missingSkills.append("🎉 No missing skills found.");
        }

        if (suggestions.length() == 0) {
            suggestions.append("🎉 Resume is well aligned with the Job Description.");
        }

        if (weaknesses.length() == 0) {
            weaknesses.append("🎉 No major weaknesses found.");
        }

        ATSResult result = new ATSResult();

        result.setScore(score);
        result.setMatchedSkills(matchedSkills.toString());
        result.setMissingSkills(missingSkills.toString());
        result.setSuggestions(suggestions.toString());
        result.setResumeQuality(resumeQuality.toString());
        result.setTechnicalScore(technicalScore);
        result.setProjectScore(projectScore);
        result.setEducationScore(educationScore);
        result.setExperienceScore(experienceScore);
        result.setFormattingScore(formattingScore);
        result.setContactScore(contactScore);
        // Match Percentage
        result.setMatchPercentage(score);

// Grade
        String grade;

        if (score >= 95) {
            grade = "A+";
        }
        else if (score >= 85) {
            grade = "A";
        }
        else if (score >= 75) {
            grade = "B+";
        }
        else if (score >= 65) {
            grade = "B";
        }
        else {
            grade = "Needs Improvement";
        }

        result.setGrade(grade);

// AI Recommendation
        String recommendation;

        if (score >= 85) {

            recommendation =
                    "Excellent Resume! You are ready to apply for Java Backend Developer roles.";

        }
        else if (score >= 70) {

            recommendation =
                    "Good Resume. Add missing technical skills to improve your ATS score.";

        }
        else {

            recommendation =
                    "Your resume needs significant improvement. Focus on adding missing skills and projects.";

        }

        result.setAiRecommendation(recommendation);

// Resume Summary
        String summary =
                "Matched Skills: " + matched +
                        "/" + totalSkills +
                        ". ATS Score: " + score +
                        "%. Grade: " + grade + ".";

        result.setResumeSummary(summary);
        result.setStrengths(strengths.toString());
        result.setWeaknesses(weaknesses.toString());

        return result;
    }
}