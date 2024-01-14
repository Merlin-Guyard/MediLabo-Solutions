package com.oc.medilabosolutionsfrontend.TI;

import com.oc.medilabosolutionsfrontend.model.Note;
import com.oc.medilabosolutionsfrontend.model.Patient;
import com.oc.medilabosolutionsfrontend.model.User;
import com.oc.medilabosolutionsfrontend.controller.PageController;
import com.oc.medilabosolutionsfrontend.service.GatewayService;
import com.oc.medilabosolutionsfrontend.service.NoteService;
import com.oc.medilabosolutionsfrontend.service.PatientService;
import com.oc.medilabosolutionsfrontend.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PageController pageController;

    @Autowired
    private PatientService patientService;

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private NoteService noteService;

    @BeforeEach
    public void initEach() throws Exception {
        patientService.deleteAll();
        noteService.deleteAll();
    }

    @Test
    public void loginPage() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/login"))
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    public void loginSuccessTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/connect")
                        .param("username", "doctor")
                        .param("password", "mdp"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/frontend/home"));
    }

    @Test
    public void loginFailureTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/connect")
                        .param("username", "doctor")
                        .param("password", "mauvaismdp"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/frontend/login"));
    }

    @Test
    public void AddViewTest() throws Exception {

        User user = new User("doctor", "mdp");
        gatewayService.login(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/addPatient"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add"));
    }

    @Test
    void homeViewTest() throws Exception {

        User user = new User("doctor", "mdp");
        gatewayService.login(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/home"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"));
    }

    @Test
    void patientViewTest() throws Exception {

        User user = new User("doctor", "mdp");
        gatewayService.login(user);

        Patient patient = new Patient(
                "John",
                "Doe",
                "1990-01-01",
                "M",
                "123 Main St",
                "123-456-7890"
        );

        patientService.addPatient(patient);
        List<Patient> patientList = patientService.getAllPatient();

        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/view/" + patientList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("view"));
    }

    @Test
    void patientUpdateTest() throws Exception {

        User user = new User("doctor", "mdp");
        gatewayService.login(user);

        Patient patient = new Patient(
                "John",
                "Doe",
                "1990-01-01",
                "M",
                "123 Main St",
                "123-456-7890"
        );

        patientService.addPatient(patient);
        patient.setFirstName("Bob");
        List<Patient> patientList = patientService.getAllPatient();


        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/updatePatient/" + patientList.get(0).getId())
                .flashAttr("patient", patient))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/frontend/home"));

        List<Patient> updatepatientList = patientService.getAllPatient();

        assertEquals(updatepatientList.get(0).getFirstName(), "Bob");
        assertEquals(updatepatientList.get(0).getLastName(), "Doe");
    }

    @Test
    void testAddPatient() throws Exception {

        User user = new User("doctor", "mdp");
        gatewayService.login(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/addPatient")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("birthdate", "1990-01-01")
                        .param("gender", "M")
                        .param("postalAddress", "123 Main St")
                        .param("phoneNumber", "123-456-7890"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/frontend/home"));

        List<Patient> patientList = patientService.getAllPatient();

        assertEquals(patientList.get(0).getFirstName(), "John");
        assertEquals(patientList.get(0).getLastName(), "Doe");
    }

    @Test
    void testGetPatient() throws Exception {

        User user = new User("doctor", "mdp");
        gatewayService.login(user);

        Patient patient = new Patient(
                "John",
                "Doe",
                "1990-01-01",
                "M",
                "123 Main St",
                "123-456-7890"
        );

        patientService.addPatient(patient);

        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/home"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"))
                .andExpect(model().attributeExists("patients")).andExpect(result -> {
                    List<Patient> patientsInModel = (List<Patient>) result.getModelAndView().getModel().get("patients");
                    assertThat(patientsInModel).isNotNull();
                    assertThat(patientsInModel).hasSize(1);
                    assertThat(patientsInModel.get(0).getFirstName()).isEqualTo("John");
                    assertThat(patientsInModel.get(0).getLastName()).isEqualTo("Doe");
                });
    }

    @Test
    void testDeletePatient() throws Exception {

        User user = new User("doctor", "mdp");
        gatewayService.login(user);

        Patient patient = new Patient(
                "John",
                "Doe",
                "1990-01-01",
                "M",
                "123 Main St",
                "123-456-7890"
        );

        patientService.addPatient(patient);
        List<Patient> patientList = patientService.getAllPatient();


        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/deletePatient/" + patientList.get(0).getId()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/frontend/home"));

        patientList = patientService.getAllPatient();

        assertTrue(patientList.isEmpty());
    }

    @Test
    void testAddNote() throws Exception {

        User user = new User("doctor", "mdp");
        gatewayService.login(user);

        Patient patient = new Patient(
                "John",
                "Doe",
                "1990-01-01",
                "M",
                "123 Main St",
                "123-456-7890"
        );

        patientService.addPatient(patient);
        List<Patient> patientList = patientService.getAllPatient();

        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/addNotes/"+ patientList.get(0).getId())
                        .param("patientId", "1")
                        .param("note", "plop"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/frontend/view/"+ patientList.get(0).getId()));

        List<Note> notes = noteService.getNotes(patientList.get(0).getId());

        assertEquals(notes.get(0).getNote(), "plop");
    }

    @Test
    void testDeleteNote() throws Exception {

        User user = new User("doctor", "mdp");
        gatewayService.login(user);

        Note note = new Note("1", "plop");

        noteService.addNotes(note);
        List<Note> noteList = noteService.getNotes(1);


        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/deleteNote/" + noteList.get(0).getId()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/frontend/home"));

        assertEquals(noteService.getNotes(1).size(),0);
    }

}