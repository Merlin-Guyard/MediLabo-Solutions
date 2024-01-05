package com.oc.medilabosolutionnotes.controller;

import com.oc.medilabosolutionnotes.model.Note;
import com.oc.medilabosolutionnotes.service.NoteService;
import org.pmw.tinylog.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping("/")
    public ResponseEntity<String> index() {
        Logger.info("Request Index");
        return ResponseEntity.ok("Application online !");
    }

    @RequestMapping("/testWrite")
    public void testWrite() {
        noteService.testWrite();
    }

    @RequestMapping("/testRead")
    public ResponseEntity<List<Note>> testRead() {
        List<Note> notes = noteService.testRead();
        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notes);
    }

    @RequestMapping("/addNote")
    public ResponseEntity<String> addNote(@RequestBody Note note) {
        try {
            noteService.addNote(note);
            return ResponseEntity.ok("Note addition success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Note addition failure");
        }
    }

    @RequestMapping("/getNote/{patientId}")
    public ResponseEntity<List<Note>> getNote(@PathVariable String patientId) {
        List<Note> notes = noteService.getNotes(patientId);
        return ResponseEntity.ok(notes);
    }

    @RequestMapping("/deleteNote/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable String id) {
        try {
            noteService.deleteById(id);
            return ResponseEntity.ok("Note deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete note");
        }
    }

    @RequestMapping("/deleteAll")
    public void deleteAll() {
        noteService.deleteAll();
    }

    @RequestMapping("/test")
    public ResponseEntity<String> addNotes() {


        Note note1 = new Note(
                "252",
                "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"
        );

        Note note2 = new Note(
                "253",
                "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"
        );

        Note note3 = new Note(
                "253",
                "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"
        );

        Note note4 = new Note(
                "254",
                "Le patient déclare qu'il fume depuis peu"
        );

        Note note5 = new Note(
                "254",
                "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"
        );

        Note note6 = new Note(
                "255",
                "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"
        );

        Note note7 = new Note(
                "255",
                "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"
        );

        Note note8 = new Note(
                "255",
                "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé"
        );

        Note note9 = new Note(
                "255",
                "Taille, Poids, Cholestérol, Vertige et Réaction"
        );

        noteService.addNote(note1);
        noteService.addNote(note2);
        noteService.addNote(note3);
        noteService.addNote(note4);
        noteService.addNote(note5);
        noteService.addNote(note6);
        noteService.addNote(note7);
        noteService.addNote(note8);
        noteService.addNote(note9);

        return ResponseEntity.status(HttpStatus.CREATED).body("Notes added successfully");
    }


}
