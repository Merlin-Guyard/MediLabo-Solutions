package com.oc.medilabosolutionsreport.dao;

import com.oc.medilabosolutionsreport.model.Patient;
import com.oc.medilabosolutionsreport.model.Properties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PatientProxy {

    private final Properties properties;

    private final RestTemplate restTemplate;

    public PatientProxy(Properties properties, RestTemplateBuilder restTemplateBuilder) {
        this.properties = properties;
        this.restTemplate = restTemplateBuilder.build();
    }

    //Get patients by id
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
}
