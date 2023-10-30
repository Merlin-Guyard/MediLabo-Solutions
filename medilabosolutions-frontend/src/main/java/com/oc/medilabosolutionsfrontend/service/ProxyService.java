package com.oc.medilabosolutionsfrontend.service;

import com.oc.medilabosolutionsfrontend.Model.Patient;
import com.oc.medilabosolutionsfrontend.Model.User;
import com.oc.medilabosolutionsfrontend.repository.UserRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProxyService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    public ProxyService(UserRepository userRepository, RestTemplateBuilder restTemplateBuilder) {
        this.userRepository = userRepository;
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

    public void deleteById(Integer id) {
    }

    public void getUser(Integer id) {
    }
}
