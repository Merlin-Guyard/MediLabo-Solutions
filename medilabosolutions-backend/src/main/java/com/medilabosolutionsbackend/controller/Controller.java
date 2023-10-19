package com.medilabosolutionsbackend.controller;

import com.medilabosolutionsbackend.model.Patient;
import com.medilabosolutionsbackend.model.User;
import com.medilabosolutionsbackend.service.PatientService;
import com.medilabosolutionsbackend.service.UserService;
import org.pmw.tinylog.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/backend")
public class Controller {

    private final PatientService patientService;

    private final UserService userService;

    public Controller(PatientService patientService, UserService userService) {
        this.patientService = patientService;
        this.userService = userService;
    }

    @RequestMapping("/")
    public ResponseEntity<String> index() {
        Logger.info("Request Index");
        return ResponseEntity.ok("Application online !");
    }

    @RequestMapping("/test")
    public ResponseEntity<String> addPatients() {
        Patient patient1 = new Patient(
                "1",
                "TestNone",
                "Test",
                "1966-12-31",
                "F",
                "1 Brookside St",
                "100-222-3333"
        );

        Patient patient2 = new Patient(
                "2",
                "TestBorderline",
                "Test",
                "1945-06-24",
                "M",
                "2 High St",
                "200-333-4444"
        );

        Patient patient3 = new Patient(
                "3",
                "TestInDanger",
                "Test",
                "2004-06-18",
                "M",
                "3 Club Road",
                "300-444-5555"
        );

        Patient patient4 = new Patient(
                "4",
                "TestEarlyOnset",
                "Test",
                "2002-06-28",
                "F",
                "4 Valley Dr",
                "400-555-6666"
        );

        User user = new User(
                "1",
                "doctor",
                "$2a$12$4QcuFdTDacNvNOf4gP5KTOEpQZA3A09XK0L3A0rgCNGLMZVPdBt4y",
                "USER"
        );


        userService.addUser(user);
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

    @RequestMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestParam(name = "username") String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }
}
