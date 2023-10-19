package com.oc.medilabosolutionsfrontend.service;

import com.oc.medilabosolutionsfrontend.Model.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
