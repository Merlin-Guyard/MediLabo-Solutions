package com.medilabosolutionsbackend.controller;

import com.medilabosolutionsbackend.model.Patient;
import com.medilabosolutionsbackend.service.PatientService;
import org.pmw.tinylog.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/backend")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping("/")
    public ResponseEntity<String> index() {
        Logger.info("Request Index");
        return ResponseEntity.ok("Application online !");
    }

    @RequestMapping("/test")
    public ResponseEntity<String> addPatients() {
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

        return ResponseEntity.status(HttpStatus.CREATED).body("Patients added successfully");
    }

    @RequestMapping("/get")
    public ResponseEntity<List<Patient>> getAllPatient() {
        List<Patient> patients = patientService.getAllPatient();
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(patients);
    }
}
