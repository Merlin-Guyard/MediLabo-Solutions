package com.oc.medilabosolutions.service;

import com.oc.medilabosolutions.model.Patient;
import com.oc.medilabosolutions.repository.PatientRepository;
import org.pmw.tinylog.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void addPatient(Patient patient){
        patientRepository.addPatient(patient);
    }

    public Patient getPatientById(String id){
        Optional<Patient> oPatient = patientRepository.getPatientById(id);
        if (oPatient.isPresent()) {
            Logger.info("Patient with id : ", id, " found");
        } else {
            Logger.info("Patient with id : ", id, " not found");
        }
        return oPatient.get();
    }

    public List<Patient> getAllPatient() {
        return patientRepository.getAllPatient();
    }
}
