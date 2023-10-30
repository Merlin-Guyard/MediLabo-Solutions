package com.oc.medilabosolutionsfrontend.TI;

import com.oc.medilabosolutionsfrontend.Model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void homeTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/frontend/home"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("patients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    List<Patient> patients = (List<Patient>) result.getModelAndView().getModel().get("patients");

                    //assert
                    assertThat(patients.get(0).getFirstName()).isEqualTo("TestNone");
                    assertThat(patients.get(0).getLastName()).isEqualTo("Test");
                    assertThat(patients.get(0).getGender()).isEqualTo("F");
                });
    }


}
