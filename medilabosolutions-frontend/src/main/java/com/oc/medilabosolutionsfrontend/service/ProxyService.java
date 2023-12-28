package com.oc.medilabosolutionsfrontend.service;

import com.oc.medilabosolutionsfrontend.Model.Note;
import com.oc.medilabosolutionsfrontend.Model.Patient;
import com.oc.medilabosolutionsfrontend.Model.Properties;
import com.oc.medilabosolutionsfrontend.Model.User;
import com.oc.medilabosolutionsfrontend.repository.UserRepository;
import org.pmw.tinylog.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;

@Service
public class ProxyService {

    private final Properties properties;

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    public ProxyService(Properties properties, UserRepository userRepository, RestTemplateBuilder restTemplateBuilder) {
        this.properties = properties;
        this.userRepository = userRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    public boolean login(User user) {
        String url = properties.getUrl() + "login";

        try {
            restTemplate.postForEntity(url, user, Void.class);
            userRepository.changeUser(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verify() {
        String url = properties.getUrl() + "login";

        User user = userRepository.getUser();
        try {
            restTemplate.postForEntity(url, user, Void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Patient> getAllPatient() {
        String url = properties.getUrl() + "backend/getPatients";

        ResponseEntity<List<Patient>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Patient>>() {
                }
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Logger.info("Patients found");
            return responseEntity.getBody();
        } else {
            Logger.info("Patients not found");
            return Collections.emptyList();
        }
    }

    public void deletePatientById(Integer id) {
        String url = properties.getUrl() + "backend/deletePatient/" + id;

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                DELETE,
                null,
                String.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Logger.info("Patient deletion success");
        } else {
            Logger.info("Patient deletion failure");
        }
    }

    public Patient getPatient(Integer id) {
        String url = properties.getUrl() + "backend/getPatient/" + id;

        ResponseEntity<Patient> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Patient.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Logger.info("Fetching patient success");
            return responseEntity.getBody();
        } else {
            Logger.info("Fetching patient failure");
            return null;
        }
    }

    public boolean updatePatient(Integer id, Patient patient) {
        String url = properties.getUrl() + "backend/updatePatient/" + id;

        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(patient), Void.class);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean addPatient(Patient patient) {
        String url = properties.getUrl() + "backend/addPatient";

        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(patient), Void.class);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public void deleteAll() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                properties.getUrl() + "backend/deleteAll",
                DELETE,
                null,
                String.class
        );


        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Logger.info("Patient deletion success");
        } else {
            Logger.info("Patient deletion failure");
        }
    }

    public List<Note> getNotes(Integer patientId) {
        String url = properties.getUrl() + "notes/getNote/" + patientId;

        ResponseEntity<List<Note>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Note>>() {}
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Logger.info("Fetching notes success");
            return responseEntity.getBody();
        } else {
            Logger.info("Fetching notes failure");
            return Collections.emptyList();
        }
    }

    public void updateNotes(Note note) {
        String url = properties.getUrl() + "notes/addNote";

        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(note), Void.class);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void deleteNoteById(String id) {
        String url = properties.getUrl() + "notes/deleteNote/" + id;

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                DELETE,
                null,
                String.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Logger.info("Note deletion success");
        } else {
            Logger.info("Note deletion failure");
        }

    }
}
