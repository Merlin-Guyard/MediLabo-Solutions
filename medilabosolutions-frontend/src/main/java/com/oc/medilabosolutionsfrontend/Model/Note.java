package com.oc.medilabosolutionsfrontend.Model;

public class Note {

    private String id;

    private String patientId;

    private String note;

    public Note() {
    }

    public Note(String id, String patientId, String note) {
        this.id = id;
        this.patientId = patientId;
        this.note = note;
    }

    public Note(String patientId, String note) {
        this.patientId = patientId;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
