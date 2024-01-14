package com.oc.medilabosolutionsreport.service;

import com.oc.medilabosolutionsreport.Exceptions.MicroserviceDownException;
import com.oc.medilabosolutionsreport.dao.KeywordFile;
import com.oc.medilabosolutionsreport.dao.NoteProxy;
import com.oc.medilabosolutionsreport.dao.PatientProxy;
import com.oc.medilabosolutionsreport.model.Note;
import com.oc.medilabosolutionsreport.model.Patient;
import org.pmw.tinylog.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReportService {

    private final NoteProxy noteProxy;

    private final PatientProxy patientProxy;

    private final KeywordFile keywordFile;

    public ReportService(NoteProxy noteProxy, PatientProxy patientProxy, KeywordFile keywordFile) {
        this.noteProxy = noteProxy;
        this.patientProxy = patientProxy;
        this.keywordFile = keywordFile;
    }

    //Generate a report
    public String makeReport(int patientId) {

        Logger.info("Making a report for patient with id : ", patientId);

        Patient patient = patientProxy.getPatient(patientId);

        List<Note> notes;
        try {
            notes = noteProxy.getNotes(patientId);
        } catch (MicroserviceDownException e) {
            return "Unable to fetch notes in order to generate a report";
        }

        Boolean age = patient.isOlderThan30yo(patient.getBirthdate());

        List<String> keywords = keywordFile.getKeywords();

        int risk = 0;
        String report = "None";

        Set<String> foundKeywords = new HashSet<>();


        for (Note note : notes) {
            for (String keyword : keywords) {
                if (note.getNote().toLowerCase().contains(keyword)) {
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
