package com.oc.medilabosolutionsreport.service;

import com.oc.medilabosolutionsreport.model.Note;
import com.oc.medilabosolutionsreport.model.Patient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReportService {

    private final ProxyService proxyService;

    public ReportService(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    public String makeReport(int patientId) {
        Patient patient = proxyService.getPatient(patientId);
        List<Note> notes = proxyService.getNotes(patientId);
        Boolean age = patient.isOlderThan30yo(patient.getBirthdate());
        List<String> keywords = Arrays.asList(
                "Hémoglobine A1C", "Microalbumine", "Taille", "Poids",
                "Fumeur", "Fumeuse", "Anormal", "Cholestérol",
                "Vertiges", "Rechute", "Réaction", "Anticorps"
        );

        int risk = 0;
        String report = "None";

        Set<String> foundKeywords = new HashSet<>();


        for (Note note : notes) {
            for (String keyword : keywords) {
                if (note.getNote().contains(keyword)) {
                    foundKeywords.add(keyword);
                }
            }
        }

        risk += foundKeywords.size();

        if (risk>=5 && !age && patient.getGender().equals("M") || risk>=7 && !age && patient.getGender().equals("F") || risk>=8 && age) {
            report = "Early onset";
        } else if (risk>=3 && !age && patient.getGender().equals("M") || risk>=4 && !age && patient.getGender().equals("F") || risk>=6 && risk<=7 && age){
            report = "In Danger";
        } else if (risk >=2 && risk<=5){
            report = "Early onset";
        }

        return report;
    }
}
