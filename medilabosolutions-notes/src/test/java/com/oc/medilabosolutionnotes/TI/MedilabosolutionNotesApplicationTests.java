package com.oc.medilabosolutionnotes.TI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.medilabosolutionnotes.controller.NoteController;
import com.oc.medilabosolutionnotes.model.Note;
import com.oc.medilabosolutionnotes.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteController noteController;

    @Autowired
    private NoteService noteService;

    @BeforeEach
    public void initEach() throws Exception {
        noteService.deleteAll();
    }

    @Test
    public void getNoteTest() throws Exception {

        Note note1 = new Note("1", "malade");
        Note note2 = new Note("1", "guérit");

        List<Note> notes = Arrays.asList(note1, note2);

        noteService.addNote(note1);
        noteService.addNote(note2);

        mockMvc.perform(get("/notes/getNote/{patientId}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].note").value("malade"))
                .andExpect(jsonPath("$[1].note").value("guérit"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void addNoteTest() throws Exception {

        Note newNote = new Note("1", "malade");

        ObjectMapper objectMapper = new ObjectMapper();
        String newNoteJson = objectMapper.writeValueAsString(newNote);

        mockMvc.perform(post("/notes/addNote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newNoteJson))
                .andExpect(status().isOk());

        List<Note> verifyNotes = noteService.getNotes("1");

        assertThat(verifyNotes.get(0).getNote().equals("malade"));
    }

    @Test
    public void deleteNoteTest() throws Exception {

        Note newNote = new Note("1", "malade");

        mockMvc.perform(delete("/notes/deleteNote/{id}", "1"))
                .andExpect(status().isOk());

        List<Note> verifyNotes = noteService.getNotes("1");

        assertThat(verifyNotes.isEmpty());
    }
}
