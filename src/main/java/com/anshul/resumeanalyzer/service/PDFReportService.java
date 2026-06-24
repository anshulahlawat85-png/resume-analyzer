package com.anshul.resumeanalyzer.service;

import com.anshul.resumeanalyzer.model.ATSResult;
import com.itextpdf.text.Document;
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

            PdfWriter.getInstance(
                    document,
                    out);

            document.open();

            document.add(
                    new Paragraph(
                            "ATS ANALYSIS REPORT"));

            document.add(
                    new Paragraph(" "));

            document.add(
                    new Paragraph(
                            "ATS Score: "
                                    + result.getScore()
                                    + "%"));

            document.add(
                    new Paragraph(" "));

            document.add(
                    new Paragraph(
                            "Matched Skills:"));

            document.add(
                    new Paragraph(
                            result.getMatchedSkills()));

            document.add(
                    new Paragraph(" "));

            document.add(
                    new Paragraph(
                            "Missing Skills:"));

            document.add(
                    new Paragraph(
                            result.getMissingSkills()));

            document.add(
                    new Paragraph(" "));

            document.add(
                    new Paragraph(
                            "Suggestions:"));

            document.add(
                    new Paragraph(
                            result.getSuggestions()));

            document.close();

            return out.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}
