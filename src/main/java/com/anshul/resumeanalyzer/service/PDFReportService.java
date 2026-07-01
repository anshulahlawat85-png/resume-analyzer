package com.anshul.resumeanalyzer.service;

import com.anshul.resumeanalyzer.model.ATSResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PDFReportService {

    public byte[] generateReport(ATSResult result) {

        try {

            Document document = new Document();

            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();

            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont =
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);

            Font headingFont =
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);

            Font normalFont =
                    FontFactory.getFont(FontFactory.HELVETICA, 12);

            // ===========================
            // Header
            // ===========================

            document.add(new Paragraph(
                    "===================================================="));

            Paragraph title =
                    new Paragraph("Resume Analyzer", titleFont);

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            Paragraph subTitle =
                    new Paragraph("ATS Analysis Report", headingFont);

            subTitle.setAlignment(Element.ALIGN_CENTER);

            document.add(subTitle);

            document.add(new Paragraph(
                    "===================================================="));

            document.add(new Paragraph(" "));

            // ===========================
            // ATS Score Card
            // ===========================

            Paragraph scoreHeading =
                    new Paragraph("ATS SCORE", headingFont);

            scoreHeading.setAlignment(Element.ALIGN_CENTER);

            document.add(scoreHeading);

            Paragraph score =
                    new Paragraph(result.getScore() + "%", titleFont);

            score.setAlignment(Element.ALIGN_CENTER);

            document.add(score);

            Paragraph grade =
                    new Paragraph(
                            "Grade : " + result.getGrade(),
                            normalFont);

            grade.setAlignment(Element.ALIGN_CENTER);

            document.add(grade);

            Paragraph match =
                    new Paragraph(
                            "Match Percentage : "
                                    + result.getMatchPercentage() + "%",
                            normalFont);

            match.setAlignment(Element.ALIGN_CENTER);

            document.add(match);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "===================================================="));

            // ===========================
            // Resume Summary
            // ===========================

            Paragraph summaryHeading =
                    new Paragraph("RESUME SUMMARY", headingFont);

            document.add(summaryHeading);

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    result.getResumeSummary(),
                    normalFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "===================================================="));

            // ===========================
            // Resume Strengths
            // ===========================

            Paragraph strengthsHeading =
                    new Paragraph("RESUME STRENGTHS", headingFont);

            document.add(strengthsHeading);

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    result.getStrengths(),
                    normalFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "===================================================="));

            // ===========================
            // Resume Weaknesses
            // ===========================

            Paragraph weaknessesHeading =
                    new Paragraph("RESUME WEAKNESSES", headingFont);

            document.add(weaknessesHeading);

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    result.getWeaknesses(),
                    normalFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "===================================================="));

            // ===========================
            // Matched Skills
            // ===========================

            Paragraph matchedHeading =
                    new Paragraph("MATCHED SKILLS", headingFont);

            document.add(matchedHeading);

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    result.getMatchedSkills(),
                    normalFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "===================================================="));

            // ===========================
            // Missing Skills
            // ===========================

            Paragraph missingHeading =
                    new Paragraph("MISSING SKILLS", headingFont);

            document.add(missingHeading);

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    result.getMissingSkills(),
                    normalFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "===================================================="));

            // ===========================
            // Suggestions
            // ===========================

            Paragraph suggestionHeading =
                    new Paragraph("SUGGESTIONS", headingFont);

            document.add(suggestionHeading);

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    result.getSuggestions(),
                    normalFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "===================================================="));

            // ===========================
            // AI Recommendation
            // ===========================

            Paragraph aiHeading =
                    new Paragraph("AI RECOMMENDATION", headingFont);

            document.add(aiHeading);

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    result.getAiRecommendation(),
                    normalFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "===================================================="));

            // ===========================
            // Footer
            // ===========================

            Paragraph footer =
                    new Paragraph(
                            "Generated by Resume Analyzer\n\nThank you for using Resume Analyzer.",
                            normalFont);

            footer.setAlignment(Element.ALIGN_CENTER);

            document.add(footer);

            document.close();

            return out.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}
