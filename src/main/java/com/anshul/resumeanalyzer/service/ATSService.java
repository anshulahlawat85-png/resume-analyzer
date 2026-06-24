package com.anshul.resumeanalyzer.service;

import com.anshul.resumeanalyzer.model.ATSResult;
import org.springframework.stereotype.Service;

@Service
public class ATSService {

    public ATSResult calculateATS(
            String resumeText,
            String jobDescription) {

        String cleanedJD = jobDescription
                .toLowerCase()
                .replace("required skills:", "")
                .replace("skills:", "")
                .replace("responsibilities:", "")
                .replace("job description:", "");

        String[] skills = cleanedJD.split("[,\n]");

        int matched = 0;
        int totalSkills = 0;

        StringBuilder matchedSkills =
                new StringBuilder();

        StringBuilder missingSkills =
                new StringBuilder();

        StringBuilder suggestions =
                new StringBuilder();

        String resume =
                resumeText.toLowerCase();

        for (String skill : skills) {

            skill = skill.trim();

            if (skill.isEmpty() || skill.length() < 3) {
                continue;
            }

            totalSkills++;

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
            }
        }

        int score = 0;

        if (totalSkills > 0) {
            score = (matched * 100) / totalSkills;
        }

        if (missingSkills.isEmpty()) {
            missingSkills.append(
                    "🎉 No missing skills found");
        }

        if (suggestions.isEmpty()) {
            suggestions.append(
                    "🎉 Resume is well aligned with the Job Description");
        }

        ATSResult result = new ATSResult();

        result.setScore(score);

        result.setMatchedSkills(
                matchedSkills.toString());

        result.setMissingSkills(
                missingSkills.toString());

        result.setSuggestions(
                suggestions.toString());

        return result;
    }
}