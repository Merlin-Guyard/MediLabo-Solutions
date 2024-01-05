package com.medilabosolutionsbackend.controller;

import com.medilabosolutionsbackend.model.Patient;
import com.medilabosolutionsbackend.service.PatientService;
import org.pmw.tinylog.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/backend")
public class Controller {

    private final PatientService patientService;


    public Controller(PatientService patientService) {
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

    @RequestMapping("/getPatients")
    public ResponseEntity<List<Patient>> getAllPatient() {
        List<Patient> patients = patientService.getAllPatient();
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(patients);
    }

    @RequestMapping("/deletePatient/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Integer id) {
        try {
            patientService.deleteById(id);
            return ResponseEntity.ok("Patient deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete patient");
        }
    }

    @RequestMapping("/getPatient/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Integer id) {
        try {
            Patient patient = patientService.getPatientById(id);
            return ResponseEntity.ok(patient);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @RequestMapping("/updatePatient/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable Integer id, @RequestBody Patient patient) {
        try {
            patientService.updatePatientById(id, patient);
            return ResponseEntity.ok("Patient update success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Patient update failure");
        }
    }

    @RequestMapping("/addPatient")
    public ResponseEntity<String> addPatient(@RequestBody Patient patient) {
        try {
            patientService.addPatient(patient);
            return ResponseEntity.ok("Patient addition success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Patient addition failure");
        }
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
