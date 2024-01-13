package com.oc.medilabosolutionsfrontend.proxy;

import com.oc.medilabosolutionsfrontend.model.Patient;
import com.oc.medilabosolutionsfrontend.model.Properties;
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
public class PatientProxy {

    private final Properties properties;

    private final RestTemplate restTemplate;

    public PatientProxy(Properties properties, RestTemplateBuilder restTemplateBuilder) {
        this.properties = properties;
        this.restTemplate = restTemplateBuilder.build();
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
}
