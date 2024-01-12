package com.oc.medilabosolutionsfrontend.dao;

import com.oc.medilabosolutionsfrontend.model.*;
import com.oc.medilabosolutionsfrontend.repository.UserRepository;
import org.pmw.tinylog.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.DELETE;

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

        try {
            ResponseEntity<Patient> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    Patient.class
            );

                Logger.info("Fetching patient success");
                return responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound notFoundException) {
            Logger.info("Patient not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found with id: " + id);
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
        ResponseEntity<String> responseEntity1 = restTemplate.exchange(
                properties.getUrl() + "backend/deleteAll",
                DELETE,
                null,
                String.class
        );

        ResponseEntity<String> responseEntity2 = restTemplate.exchange(
                properties.getUrl() + "notes/deleteAll",
                DELETE,
                null,
                String.class
        );
    }

    public List<Note> getNotes(Integer patientId) {
        String url = properties.getUrl() + "notes/getNote/" + patientId;

        try {
        ResponseEntity<List<Note>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Note>>() {
                }
        );
            return responseEntity.getBody();
        } catch (Exception e){
            throw new NotesFetchException("Unable to fetch notes for patientId: " + patientId);
        }
    }

    public void addNotes(Note note) {
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

    public String getReport(Integer patientId) {

        String url = properties.getUrl() + "report/getReport/" + patientId;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<String>() {}
            );

                Logger.info("Fetching report success");
                return responseEntity.getBody();
        } catch (Exception e) {
            Logger.info("Fetching report failure");
            return "";
        }
    }
}
