package com.oc.medilabosolutionsfrontend.controller;

import com.oc.medilabosolutionsfrontend.model.Note;
import com.oc.medilabosolutionsfrontend.model.Patient;
import com.oc.medilabosolutionsfrontend.service.GatewayService;
import com.oc.medilabosolutionsfrontend.service.NoteService;
import com.oc.medilabosolutionsfrontend.service.PatientService;
import com.oc.medilabosolutionsfrontend.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/frontend")
public class TestController {

    private final PatientService patientService;

    private final NoteService noteService;

    private final ReportService reportService;

    private final GatewayService gatewayService;


    public TestController(PatientService patientService, NoteService noteService, ReportService reportService, GatewayService gatewayService) {
        this.patientService = patientService;
        this.noteService = noteService;
        this.reportService = reportService;
        this.gatewayService = gatewayService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(Model model) {

        List<Patient> patients = patientService.getAllPatient();

        int patient1 = patients.get(0).getId();
        int patient2 = patients.get(1).getId();
        int patient3 = patients.get(2).getId();
        int patient4 = patients.get(3).getId();

        Note note1 = new Note(
                Integer.toString(patient1),
                "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"
        );

        Note note2 = new Note(
                Integer.toString(patient2),
                "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"
        );

        Note note3 = new Note(
                Integer.toString(patient2),
                "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"
        );

        Note note4 = new Note(
                Integer.toString(patient3),
                "Le patient déclare qu'il fume depuis peu"
        );

        Note note5 = new Note(
                Integer.toString(patient3),
                "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"
        );

        Note note6 = new Note(
                Integer.toString(patient4),
                "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"
        );

        Note note7 = new Note(
                Integer.toString(patient4),
                "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"
        );

        Note note8 = new Note(
                Integer.toString(patient4),
                "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"
        );

        Note note9 = new Note(
                Integer.toString(patient4),
                "Taille, Poids, Cholestérol, Vertige et Réaction"
        );

        noteService.addNotes(note1);
        noteService.addNotes(note2);
        noteService.addNotes(note3);
        noteService.addNotes(note4);
        noteService.addNotes(note5);
        noteService.addNotes(note6);
        noteService.addNotes(note7);
        noteService.addNotes(note8);
        noteService.addNotes(note9);

        return new ResponseEntity<>("All notes added", HttpStatus.OK);
    }

    //Send Delete request (testing with Postman only)
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {

        patientService.deleteAll();

        return new ResponseEntity<>("All patients deleted", HttpStatus.OK);
    }
}
