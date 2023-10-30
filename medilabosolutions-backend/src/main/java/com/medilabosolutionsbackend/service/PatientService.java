package com.medilabosolutionsbackend.service;

import com.medilabosolutionsbackend.model.Patient;
import com.medilabosolutionsbackend.repository.PatientRepository;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public void addPatient(Patient patient){
        patientRepository.save(patient);
    }

    public Patient getPatientById(int id){
        Optional<Patient> oPatient = patientRepository.findById(id);
        if (oPatient.isPresent()) {
            Logger.info("Patient with id : " + id + " found");
        } else {
            Logger.info("Patient with id : " + id + " not found");
        }
        return oPatient.get();
    }

    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

    public void deleteById(Integer id) {
        patientRepository.deleteById(id);
    }

    public void updatePatientById(Integer id, Patient patient) {
        patient.setId(id);
        patientRepository.save(patient);
    }
}
