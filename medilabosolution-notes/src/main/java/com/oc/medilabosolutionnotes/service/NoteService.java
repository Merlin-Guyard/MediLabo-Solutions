package com.oc.medilabosolutionnotes.service;

import com.oc.medilabosolutionnotes.model.Note;
import com.oc.medilabosolutionnotes.repository.NoteRepository;
import org.pmw.tinylog.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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

    public void addNote(Note note) {
        note.setId(null);
        noteRepository.save(note);
    }

    public List<Note> getNotes(String patientId) {
        List<Note> notes = noteRepository.findAll()
                .stream()
                .filter(note -> note.getPatientId().equals(patientId))
                .collect(Collectors.toList());;

        if (!notes.isEmpty()){
            Logger.info("Notes from patient with id : " + patientId + " found");
            return notes;
        } else {
            Logger.info("Notes from patient with id : " + patientId + " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notes not found from patient with id: " + patientId);
        }
    }

    public void deleteById(String id) {
        noteRepository.deleteById(id);
    }

    public void deleteAll() {
        noteRepository.deleteAll();
    }
}
