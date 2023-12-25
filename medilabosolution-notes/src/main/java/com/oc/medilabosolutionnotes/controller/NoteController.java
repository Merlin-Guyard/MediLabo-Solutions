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
        if (notes.isEmpty()){
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


}
