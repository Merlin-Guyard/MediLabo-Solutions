package com.oc.medilabosolutionnotes.controller;

import com.oc.medilabosolutionnotes.model.Note;
import com.oc.medilabosolutionnotes.service.NoteService;
import org.pmw.tinylog.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class TestController {

    private final NoteService noteService;

    public TestController(NoteService noteService) {
        this.noteService = noteService;
    }

    //Delete all note (for testing with Postman)
    @RequestMapping("/deleteAll")
    public void deleteAll() {
        Logger.info("Deleting all notes");

        noteService.deleteAll();
    }
}
