package com.oc.medilabosolutionsfrontend.Model;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Url {

    @Value("${url}")
    private String url;

    public Url() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
