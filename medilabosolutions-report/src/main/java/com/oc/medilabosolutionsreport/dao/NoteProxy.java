package com.oc.medilabosolutionsreport.dao;

import com.oc.medilabosolutionsreport.Exceptions.MicroserviceDownException;
import com.oc.medilabosolutionsreport.model.Note;
import com.oc.medilabosolutionsreport.model.Properties;
import org.pmw.tinylog.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class NoteProxy {

    private final Properties properties;

    private final RestTemplate restTemplate;

    public NoteProxy(Properties properties, RestTemplateBuilder restTemplateBuilder) {
        this.properties = properties;
        this.restTemplate = restTemplateBuilder.build();
    }

    //Get notes by patient's id
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
        } catch (Exception e) {
            Logger.error("Failed to fetch notes for patient with id : ", patientId);

            throw new MicroserviceDownException("Note service is unavailable");
        }

    }
}
