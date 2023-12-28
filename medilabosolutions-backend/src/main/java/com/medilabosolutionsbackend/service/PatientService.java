package com.medilabosolutionsbackend.service;

import com.medilabosolutionsbackend.model.Patient;
import com.medilabosolutionsbackend.repository.PatientRepository;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            return oPatient.get();
        } else {
            Logger.info("Patient with id : " + id + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found with id: " + id);
        }

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

    public void deleteAll() {
        patientRepository.deleteAll();
    }
}
