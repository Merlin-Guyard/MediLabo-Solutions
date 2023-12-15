package com.oc.medilabosolutionnotes.service;

import com.oc.medilabosolutionnotes.model.Note;
import com.oc.medilabosolutionnotes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void testWrite() {
        Note note = new Note();
        note.setNote("plop");

        noteRepository.save(note);
    }

    public List<Note> testRead() {
        return noteRepository.findAll();
    }
}
