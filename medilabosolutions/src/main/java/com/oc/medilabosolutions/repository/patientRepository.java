package com.oc.medilabosolutions.repository;

import com.oc.medilabosolutions.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class patientRepository {
    List<Patient> patientList = new ArrayList<>();

    public void addPatient(Patient patient){
        patientList.add(patient);
    }

    public Patient getPatientById(String id){
        for (Patient patient : patientList) {
            if (patient.getId() != null && patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }

    public List<Patient> getAllpatient(Patient patient){
        return  patientList;
    }
}
