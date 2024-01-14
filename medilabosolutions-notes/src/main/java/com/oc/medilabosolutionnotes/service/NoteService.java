package com.oc.medilabosolutionnotes.service;

import com.oc.medilabosolutionnotes.model.Note;
import com.oc.medilabosolutionnotes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void addNote(Note note) {
        note.setId(null);
        noteRepository.save(note);
    }

    public List<Note> getNotes(String patientId) {
        List<Note> notes = noteRepository.findAll();
        return notes.stream()
                .filter(note -> note.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        noteRepository.deleteById(id);
    }

    public void deleteAll() {
        noteRepository.deleteAll();
    }
}
