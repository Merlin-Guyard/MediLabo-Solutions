package com.medilabosolutionsbackend.controller;

import com.medilabosolutionsbackend.model.Patient;
import com.medilabosolutionsbackend.service.PatientService;
import org.pmw.tinylog.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/backend")
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

    @RequestMapping("/test")
    public void addPatients() {
        Patient patient1 = new Patient(
                "TestNone",
                "Test",
                "1966-12-31",
                "F",
                "1 Brookside St",
                "100-222-3333"
        );

        Patient patient2 = new Patient(
                "TestBorderline",
                "Test",
                "1945-06-24",
                "M",
                "2 High St",
                "200-333-4444"
        );

        Patient patient3 = new Patient(
                "TestInDanger",
                "Test",
                "2004-06-18",
                "M",
                "3 Club Road",
                "300-444-5555"
        );

        Patient patient4 = new Patient(
                "TestEarlyOnset",
                "Test",
                "2002-06-28",
                "F",
                "4 Valley Dr",
                "400-555-6666"
        );

        patientService.addPatient(patient1);
        patientService.addPatient(patient2);
        patientService.addPatient(patient3);
        patientService.addPatient(patient4);
    }

    @RequestMapping("/get")
    public List<Patient> getPatient() {
        return patientService.getAllPatient();
    }
}
