package com.medilabosolutionsbackend.TI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabosolutionsbackend.controller.Controller;
import com.medilabosolutionsbackend.model.Patient;
import com.medilabosolutionsbackend.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Controller controller;

	@Autowired
	private PatientService patientService;

	@BeforeEach
	public void initEach() throws Exception {
		patientService.deleteAll();
	}

	@Test
	public void getAllPatientTest() throws Exception {

		Patient patient1 = new Patient(
				"John",
				"Doe",
				LocalDate.parse("1990-01-01"),
				"M",
				"123 Main St",
				"123-456-7890"
		);

		Patient patient2 = new Patient(
				"Bob",
				"Dupont",
				LocalDate.parse("1990-01-01"),
				"M",
				"123 Main St",
				"123-456-7890"
		);

		patientService.addPatient(patient1);
		patientService.addPatient(patient2);

		mockMvc.perform(get("/backend/getPatients"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].firstName").value("John"))
				.andExpect(jsonPath("$[1].firstName").value("Bob"))
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void getPatientByIdTest() throws Exception {

		Patient patient1 = new Patient(
				"John",
				"Doe",
				LocalDate.parse("1990-01-01"),
				"M",
				"123 Main St",
				"123-456-7890"
		);

		patientService.addPatient(patient1);
		List<Patient> idPatient = patientService.getAllPatient();

		mockMvc.perform(get("/backend/getPatient/{id}", idPatient.get(0).getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("John"));
	}

	@Test
	public void addPatientTest() throws Exception {

		Patient patient1 = new Patient(
				"John",
				"Doe",
				LocalDate.parse("1990-01-01"),
				"M",
				"123 Main St",
				"123-456-7890"
		);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		String patientJson = objectMapper.writeValueAsString(patient1);

		mockMvc.perform(post("/backend/addPatient")
						.contentType(MediaType.APPLICATION_JSON)
						.content(patientJson))
				.andExpect(status().isOk());

		List<Patient> verifyPatient = patientService.getAllPatient();

		assertThat(verifyPatient.size()==1);
		assertThat(verifyPatient.get(0).getFirstName().equals("John"));
	}

	@Test
	public void updatePatientTest() throws Exception {

		Patient existingPatient = new Patient(
				"John",
				"Doe",
				LocalDate.parse("1990-01-01"),
				"M",
				"123 Main St",
				"123-456-7890"
		);
		patientService.addPatient(existingPatient);


		Patient updatedPatient = new Patient(
				"John",
				"Doe",
				LocalDate.parse("1990-01-01"),
				"F",
				"123 Main St",
				"123-456-7890"
		);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		String updatedPatientJson = objectMapper.writeValueAsString(updatedPatient);

		List<Patient> idPatient = patientService.getAllPatient();

		mockMvc.perform(put("/backend/updatePatient/{id}", idPatient.get(0).getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(updatedPatientJson))
				.andExpect(status().isOk());

		List<Patient> verifyPatient = patientService.getAllPatient();

		assertThat(verifyPatient.get(0).getGender().equals("F"));
	}

	@Test
	public void deletePatientTest() throws Exception {

		Patient patientToDelete = new Patient(
				"John",
				"Doe",
				LocalDate.parse("1990-01-01"),
				"M",
				"123 Main St",
				"123-456-7890"
		);
		patientService.addPatient(patientToDelete);

		List<Patient> idPatient = patientService.getAllPatient();

		mockMvc.perform(delete("/backend/deletePatient/{id}", idPatient.get(0).getId()))
				.andExpect(status().isOk());

		List<Patient> verifyPatient = patientService.getAllPatient();
		assertThat(verifyPatient.isEmpty());
	}
}
