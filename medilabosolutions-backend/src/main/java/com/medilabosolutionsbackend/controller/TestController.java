package com.medilabosolutionsbackend.controller;

import com.medilabosolutionsbackend.model.Patient;
import com.medilabosolutionsbackend.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/backend")
public class TestController {

    private final PatientService patientService;


    public TestController(PatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping("/test")
    public ResponseEntity<String> addPatients() {


        Patient patient1 = new Patient(
                "TestNone",
                "Test",
                LocalDate.parse("1966-12-31"),
                "F",
                "1 Brookside St",
                "100-222-3333"
        );

        Patient patient2 = new Patient(
                "TestBorderline",
                "Test",
                LocalDate.parse("1945-06-24"),
                "M",
                "2 High St",
                "200-333-4444"
        );

        Patient patient3 = new Patient(
                "TestInDanger",
                "Test",
                LocalDate.parse("2004-06-18"),
                "M",
                "3 Club Road",
                "300-444-5555"
        );

        Patient patient4 = new Patient(
                "TestEarlyOnset",
                "Test",
                LocalDate.parse("2002-06-28"),
                "F",
                "4 Valley Dr",
                "400-555-6666"
        );

        patientService.addPatient(patient1);
        patientService.addPatient(patient2);
        patientService.addPatient(patient3);
        patientService.addPatient(patient4);

        return ResponseEntity.status(HttpStatus.CREATED).body("Patients and user added successfully");
    }

    @RequestMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {
        try {
            patientService.deleteAll();
            return ResponseEntity.ok("Patients deletion success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Patients deletion failure");
        }
    }
}
