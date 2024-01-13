package com.oc.medilabosolutionsreport.service;

import com.oc.medilabosolutionsreport.model.Note;
import com.oc.medilabosolutionsreport.model.Patient;
import com.oc.medilabosolutionsreport.model.Properties;
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

@Service
public class ProxyService {

    private final Properties properties;


    private final RestTemplate restTemplate;

    public ProxyService(Properties properties, RestTemplateBuilder restTemplateBuilder) {
        this.properties = properties;
        this.restTemplate = restTemplateBuilder.build();
    }

    public Patient getPatient(int id) {
        String url = properties.getUrl() + "backend/getPatient/" + id;

        try {
            ResponseEntity<Patient> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    Patient.class
            );
            return responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound notFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found with id: " + id);
        }
    }

    public List<Note> getNotes(Integer patientId) {
        String url = properties.getUrl() + "notes/getNote/" + patientId;

        try {
        ResponseEntity<List<Note>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Note>>() {}
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return Collections.emptyList();
        }} catch (Exception e){
            return Collections.emptyList();
        }

    }
}
