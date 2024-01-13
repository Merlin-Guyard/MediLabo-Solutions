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

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    //Check if application is on
    @RequestMapping("/")
    public ResponseEntity<String> healthCheck() {

        Logger.info("Checking if Application is online");

        return ResponseEntity.ok("Application online !");
    }

    //Add a note
    @RequestMapping("/addNote")
    public void addNote(@RequestBody Note note) {

        Logger.info("Adding note");

        noteService.addNote(note);
    }

    //Get all notes from a patient with its id
    @RequestMapping("/getNote/{patientId}")
    public ResponseEntity<List<Note>> getNote(@PathVariable String patientId) {

        Logger.info("Getting note for patient with id : ", patientId);

        List<Note> notes = noteService.getNotes(patientId);

        return ResponseEntity.ok(notes);
    }

    //Delete note by id
    @RequestMapping("/deleteNote/{id}")
    public void deleteNote(@PathVariable String id) {

        Logger.info("Deleting note with id : ", id);

        noteService.deleteById(id);
    }
}
