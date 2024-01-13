package com.oc.medilabosolutionsfrontend.proxy;

import com.oc.medilabosolutionsfrontend.model.Note;
import com.oc.medilabosolutionsfrontend.model.NotesFetchException;
import com.oc.medilabosolutionsfrontend.model.Properties;
import com.oc.medilabosolutionsfrontend.repository.UserRepository;
import org.pmw.tinylog.Logger;
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
}
