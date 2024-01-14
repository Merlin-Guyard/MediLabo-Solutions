package com.oc.medilabosolutionsfrontend.proxy;

import com.oc.medilabosolutionsfrontend.model.Properties;
import com.oc.medilabosolutionsfrontend.model.User;
import com.oc.medilabosolutionsfrontend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GatewayProxy {

    private final Properties properties;

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    public GatewayProxy(Properties properties, UserRepository userRepository, RestTemplate restTemplate) {
        this.properties = properties;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public boolean login(User user) {
        String url = properties.getUrl() + "login";

        try {
            restTemplate.postForEntity(url, user, Void.class);
            userRepository.changeUser(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verify() {
        String url = properties.getUrl() + "login";

        User user = userRepository.getUser();
        try {
            restTemplate.postForEntity(url, user, Void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
