package com.medilabosolutions.backend.controller;

import com.medilabosolutions.backend.model.Patient;
import com.medilabosolutions.backend.service.PatientService;
import org.pmw.tinylog.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping("/")
    public String index() {
        Logger.info("Request Index");
        return "Application online !";
    }

    @RequestMapping("/add")
    public void addPatient() {
        Patient patient = new Patient(
                "John",
                "Doe",
                "1990-05-15",
                "Male",
                "75000",
                "555-123-4567"
        );
        patientService.addPatient(patient);
    }

    @RequestMapping("/get")
    public List<Patient> getPatient() {
        return patientService.getAllPatient();
    }
}
