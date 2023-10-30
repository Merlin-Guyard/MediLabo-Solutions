package com.oc.medilabosolutionsfrontend.service;

import com.oc.medilabosolutionsfrontend.Model.Patient;
import com.oc.medilabosolutionsfrontend.Model.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProxyService {

    private final RestTemplate restTemplate;

    public ProxyService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public User getUsers(String username) {
        String url = "http://localhost:8080/backend/getUser?username=" + username;

        ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
//            return "Erreur lors de la requête HTTP.";
            return null;
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
            return responseEntity.getBody();
        } else {
            return null;
        }
    }

    public void login(User user) {
        String url = "http://localhost:8080/login";

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, user, Void.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Authentification réussie");
        } else {
            System.out.println("Échec de l'authentification.");
        }
    }
}
