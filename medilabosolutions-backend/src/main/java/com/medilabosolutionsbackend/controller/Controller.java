package com.medilabosolutionsbackend.controller;

import com.medilabosolutionsbackend.model.Patient;
import com.medilabosolutionsbackend.service.PatientService;
import org.pmw.tinylog.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/backend")
public class Controller {

    private final PatientService patientService;


    public Controller(PatientService patientService) {
        this.patientService = patientService;
    }

    //Check if application is on
    @RequestMapping("/")
    public ResponseEntity<String> healthCheck() {

        Logger.info("Checking if Application is online");

        return ResponseEntity.ok("Application online !");
    }

    //Get all patients
    @RequestMapping("/getPatients")
    public ResponseEntity<List<Patient>> getAllPatient() {

        Logger.info("Getting all patients");

        return ResponseEntity.ok(patientService.getAllPatient());
    }

    //Get patient by id
    @RequestMapping("/getPatient/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Integer id) {

        Logger.info("Getting patient with id : ", id);

        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    //Add patient
    @RequestMapping("/addPatient")
    public void addPatient(@RequestBody Patient patient) {

        Logger.info("Adding new patient");

        patientService.addPatient(patient);
    }

    //Update patient
    @RequestMapping("/updatePatient/{id}")
    public void updatePatient(@PathVariable Integer id, @RequestBody Patient patient) {

        Logger.info("Updating patient with id : ", id);

        patientService.updatePatientById(id, patient);
    }

    //Delete patient by id
    @RequestMapping("/deletePatient/{id}")
    public void deletePatient(@PathVariable Integer id) {

        Logger.info("Deleting patient with id : ", id);

        patientService.deleteById(id);
    }
}
