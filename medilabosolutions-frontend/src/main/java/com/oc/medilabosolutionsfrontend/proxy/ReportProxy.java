package com.oc.medilabosolutionsfrontend.proxy;

import com.oc.medilabosolutionsfrontend.Exceptions.MicroserviceDownException;
import com.oc.medilabosolutionsfrontend.model.Properties;
import org.pmw.tinylog.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportProxy {

    private final Properties properties;

    private final RestTemplate restTemplate;

    public ReportProxy(Properties properties, RestTemplateBuilder restTemplateBuilder) {
        this.properties = properties;
        this.restTemplate = restTemplateBuilder.build();
    }

    //Get a report
    public String getReport(Integer patientId) {

        String url = properties.getUrl() + "report/getReport/" + patientId;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<String>() {}
            );

            Logger.info("Fetching report success");
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new MicroserviceDownException("Report service is unavailable");
        }
    }
}
