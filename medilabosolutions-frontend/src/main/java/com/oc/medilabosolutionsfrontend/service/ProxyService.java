package com.oc.medilabosolutionsfrontend.service;

import com.oc.medilabosolutionsfrontend.Model.Patient;
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.DELETE;

@Service
public class ProxyService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    public ProxyService(UserRepository userRepository, RestTemplateBuilder restTemplateBuilder) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplateBuilder.build();
    }
    public boolean login(User user) {
        String url = "http://localhost:8080/login";

        try {
            restTemplate.postForEntity(url, user, Void.class);
            userRepository.changeUser(user);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean verify() {
        String url = "http://localhost:8080/login";

        User user = userRepository.getUser();
        try {
            restTemplate.postForEntity(url, user, Void.class);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public List<Patient> getAllPatient() {
        String url = "http://localhost:8080/backend/getPatients";

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

    public void deleteById(Integer id) {
        String url = "http://localhost:8080/backend/deletePatient/" + id;

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
        String url = "http://localhost:8080/backend/getPatient/" + id;

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
        String url = "http://localhost:8080/backend/updatePatient/" +id;

        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(patient), Void.class);
            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean addPatient(Patient patient) {
        String url = "http://localhost:8080/backend/addPatient";

        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(patient), Void.class);
            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public void deleteALL() {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "http://localhost:8080/backend/deleteAll",
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
}
