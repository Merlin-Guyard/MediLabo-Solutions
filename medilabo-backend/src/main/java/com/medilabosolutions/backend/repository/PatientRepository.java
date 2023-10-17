package com.medilabosolutions.backend.repository;

import com.medilabosolutions.backend.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepository {
    List<Patient> patientList = new ArrayList<>();

    public void addPatient(Patient patient) {
        patientList.add(patient);
    }

    public Optional<Patient> getPatientById(String id) {
        for (Patient patient : patientList) {
            if (patient.getId().equals(id)) {
                return Optional.of(patient);
            }
        }
        return Optional.empty();
    }

    public List<Patient> getAllPatient() {
        return patientList;
    }

    public void updatePatient(String id, Patient updatedPatient) {
        for (int i = 0; i < patientList.size(); i++) {
            Patient patient = patientList.get(i);
            if (patient.getId().equals(id)) {
                patientList.set(i, updatedPatient);
            }
        }
    }

    public void deletePatientById(String id) {
        patientList.removeIf(patient -> patient.getId().equals(id));
    }
}
