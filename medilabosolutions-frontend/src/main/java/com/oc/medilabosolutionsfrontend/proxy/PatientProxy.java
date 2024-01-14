package com.oc.medilabosolutionsfrontend.proxy;

import com.oc.medilabosolutionsfrontend.Exceptions.MicroserviceDownException;
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

        try {
            ResponseEntity<List<Patient>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Patient>>() {
                    }
            );

            return responseEntity.getBody();
        } catch (Exception e) {
            throw new MicroserviceDownException("Patient service is unavailable");
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

            return responseEntity.getBody();
        } catch (Exception e) {
            throw new MicroserviceDownException("Patient service is unavailable");
        }
    }

    public void addPatient(Patient patient) {
        String url = properties.getUrl() + "backend/addPatient";

        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(patient), Void.class);
        } catch (Exception e) {
            throw new MicroserviceDownException("Patient service is unavailable");
        }
    }

    public void updatePatient(Integer id, Patient patient) {
        String url = properties.getUrl() + "backend/updatePatient/" + id;

        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(patient), Void.class);
        } catch (Exception e) {
            throw new MicroserviceDownException("Patient service is unavailable");
        }
    }

    public void deleteAll() {
        String url = properties.getUrl() + "backend/deleteAll";

        try {
            ResponseEntity<String> responseEntity1 = restTemplate.exchange(
                    url,
                    DELETE,
                    null,
                    String.class
            );
        } catch (Exception e) {
            throw new MicroserviceDownException("Patient service is unavailable");
        }
    }

    public void deletePatientById(Integer id) {
        String url = properties.getUrl() + "backend/deletePatient/" + id;

        try {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                DELETE,
                null,
                String.class
        );

        } catch (Exception e) {
            throw new MicroserviceDownException("Patient service is unavailable");
        }
    }
}
