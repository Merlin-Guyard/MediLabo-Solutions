package com.oc.medilabosolutionsfrontend.TI;

import com.oc.medilabosolutionsfrontend.Model.Patient;
import com.oc.medilabosolutionsfrontend.Model.User;
import com.oc.medilabosolutionsfrontend.controller.PageController;
import com.oc.medilabosolutionsfrontend.service.ProxyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PageController pageController;

    @Mock
    private ProxyService proxyService;

    @BeforeEach
    public void initEach() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/frontend/deleteAll"));
    }

    @Test
    public void loginSuccessTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/connect")
                        .param("username", "doctor")
                        .param("password", "mdp"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/frontend/home"));
    }

    @Test
    public void loginFailureTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/connect")
                        .param("username", "doctor")
                        .param("password", "mauvaismdp"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/frontend/login"));
    }

    @Test
    public void AddViewTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/connect")
                .param("username", "doctor")
                .param("password", "mdp"));

        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/addPatient"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add"));
    }

    @Test
    void homeViewTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/connect")
                .param("username", "doctor")
                .param("password", "mdp"));

        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"));
    }

    @Test
    void testAddAndGetPatient() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/connect")
                .param("username", "doctor")
                .param("password", "mdp"));

        mockMvc.perform(MockMvcRequestBuilders.post("/frontend/addPatient")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("birthdate", "1990-01-01")
                        .param("gender", "Male")
                        .param("postalAddress", "123 Main St")
                        .param("phoneNumber", "123-456-7890"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/frontend/home"));

        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"))
                .andExpect(model().attributeExists("patients")).andExpect(result -> {
                    List<Patient> patientsInModel = (List<Patient>) result.getModelAndView().getModel().get("patients");
                    assertThat(patientsInModel).isNotNull();
                    assertThat(patientsInModel).hasSize(1);
                    assertThat(patientsInModel.get(0).getFirstName()).isEqualTo("John");
                    assertThat(patientsInModel.get(0).getLastName()).isEqualTo("Doe");
                });
    }


}