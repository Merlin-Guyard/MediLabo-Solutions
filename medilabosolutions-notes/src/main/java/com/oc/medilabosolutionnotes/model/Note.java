package com.oc.medilabosolutionnotes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "notes")
public class Note {

    @Id
    @MongoId
    private String id;

    private String patientId;

    private String note;

    public Note() {
    }

    public Note(String patientId, String note) {
        this.patientId = patientId;
        this.note = note;
    }

    public Note(String id, String patientId, String note) {
        this.id = id;
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
