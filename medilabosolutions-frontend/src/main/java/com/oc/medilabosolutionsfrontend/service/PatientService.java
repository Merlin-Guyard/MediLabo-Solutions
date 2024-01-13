package com.oc.medilabosolutionsfrontend.service;

import com.oc.medilabosolutionsfrontend.model.Patient;
import com.oc.medilabosolutionsfrontend.proxy.GatewayProxy;
import com.oc.medilabosolutionsfrontend.proxy.PatientProxy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientProxy patientProxy;

    public PatientService(PatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }

    public List<Patient> getAllPatient() {
        return patientProxy.getAllPatient();
    }

    public Patient getPatient(Integer id) {
        return patientProxy.getPatient(id);
    }

    public void addPatient(Patient patient) {
        patientProxy.addPatient(patient);
    }

    public void updatePatient(Integer id, Patient patient) {
        patientProxy.updatePatient(id, patient);
    }

    public void deletePatientById(Integer id) {
        patientProxy.deletePatientById(id);
    }

    public void deleteAll() {
        patientProxy.deleteAll();
    }
}
