package com.oc.medilabosolutionsfrontend.service;

import com.oc.medilabosolutionsfrontend.model.Note;
import com.oc.medilabosolutionsfrontend.proxy.NoteProxy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteProxy noteProxy;

    public NoteService(NoteProxy noteProxy) {
        this.noteProxy = noteProxy;
    }

    public List<Note> getNotes(int id) {
        return noteProxy.getNotes(id);
    }

    public void addNotes(Note note) {
        noteProxy.addNotes(note);
    }

    public void deleteNoteById(String id) {
        noteProxy.deleteNoteById(id);
    }

    public void deleteAll() {
        noteProxy.deleteAll();
    }
}
