package com.oc.medilabosolutionsfrontend.TI;

import com.oc.medilabosolutionsfrontend.Model.Patient;
import com.oc.medilabosolutionsfrontend.Model.User;
import com.oc.medilabosolutionsfrontend.controller.PageController;
import com.oc.medilabosolutionsfrontend.service.ProxyService;
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
    private ProxyService proxyService;

    @BeforeEach
    public void initEach() throws Exception {
        proxyService.deleteALL();
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
        proxyService.login(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/addPatient"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add"));
    }

    @Test
    void homeViewTest() throws Exception {

        User user = new User("doctor", "mdp");
        proxyService.login(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/home"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"));
    }

    @Test
    void testAddPatient() throws Exception {

        User user = new User("doctor", "mdp");
        proxyService.login(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/addPatient")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("birthdate", "1990-01-01")
                        .param("gender", "M")
                        .param("postalAddress", "123 Main St")
                        .param("phoneNumber", "123-456-7890"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/frontend/home"));

        List<Patient> patientList = proxyService.getAllPatient();

        assertEquals(patientList.get(0).getFirstName(), "John");
        assertEquals(patientList.get(0).getLastName(), "Doe");
    }

    @Test
    void testGetPatient() throws Exception {

        User user = new User("doctor", "mdp");
        proxyService.login(user);

        Patient patient = new Patient(
                "John",
                "Doe",
                "1990-01-01",
                "M",
                "123 Main St",
                "123-456-7890"
        );

        proxyService.addPatient(patient);

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

    //TODO : error not found, found, empty list it goes in wrong if
//    @Test
//    void testDeletePatient() throws Exception {
//
//        User user = new User("doctor", "mdp");
//        proxyService.login(user);
//
//        Patient patient = new Patient(
//                "John",
//                "Doe",
//                "1990-01-01",
//                "M",
//                "123 Main St",
//                "123-456-7890"
//        );
//
//        proxyService.addPatient(patient);
//        List<Patient> patientList = proxyService.getAllPatient();
//
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/delete/" + patientList.get(0).getId()))
//                .andExpect(status().isFound())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/frontend/home"));
//
//        assertEquals(proxyService.getAllPatient().size(),0);
//    }


}