package com.oc.medilabosolutionsfrontend.Model;

public class Note {

    private String patientId;

    private String note;

    public Note() {
    }

    public Note(String patientId, String note) {
        this.patientId = patientId;
        this.note = note;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
