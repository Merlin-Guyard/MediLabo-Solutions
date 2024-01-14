package com.oc.medilabosolutionsfrontend.proxy;

import com.oc.medilabosolutionsfrontend.model.Note;
import com.oc.medilabosolutionsfrontend.Exceptions.MicroserviceDownException;
import com.oc.medilabosolutionsfrontend.model.Properties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.DELETE;

@Service
public class NoteProxy {

    private final Properties properties;

    private final RestTemplate restTemplate;

    public NoteProxy(Properties properties, RestTemplateBuilder restTemplateBuilder) {
        this.properties = properties;
        this.restTemplate = restTemplateBuilder.build();
    }

    //Get all note from patient with id
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
            throw new MicroserviceDownException("Note service is unavailable");
        }
    }

    //Add a note
    public void addNotes(Note note) {
        String url = properties.getUrl() + "notes/addNote";

        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(note), Void.class);
        } catch (Exception e) {
            throw new MicroserviceDownException("Note service is unavailable");
        }

    }

    //Delete a note with id
    public void deleteNoteById(String id) {
        String url = properties.getUrl() + "notes/deleteNote/" + id;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    DELETE,
                    null,
                    String.class
            );
        } catch (Exception e) {
            throw new MicroserviceDownException("Note service is unavailable");
        }
    }

    public void deleteAll() {
        String url = properties.getUrl() + "notes/deleteAll";

        try {
            ResponseEntity<String> responseEntity1 = restTemplate.exchange(
                    url,
                    DELETE,
                    null,
                    String.class
            );
        } catch (Exception e) {
            throw new MicroserviceDownException("Note service is unavailable");
        }
    }
}
