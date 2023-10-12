package com.oc.medilabosolutions.repository;

import com.oc.medilabosolutions.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientRepository {
    List<Patient> patientList = new ArrayList<>();

    public void addPatient(Patient patient){
        patientList.add(patient);
    }

    public Optional<Patient> getPatientById(String id){
        for (Patient patient : patientList) {
            if (patient.getId() != null && patient.getId().equals(id)) {
                return Optional.of(patient);
            }
        }
        return null;
    }

    public List<Patient> getAllPatient(){
        return  patientList;
    }
}
