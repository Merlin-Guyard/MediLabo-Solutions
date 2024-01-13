package com.oc.medilabosolutionsreport.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.medilabosolutionsreport.Exceptions.FetchingKeywordsException;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class KeywordFile {

    private final ResourceLoader resourceLoader;

    public KeywordFile(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<String> getKeywords() {
        try {
            Resource resource = resourceLoader.getResource("classpath:keywords.json");

            ObjectMapper objectMapper = new ObjectMapper();
            String[] champsArray = objectMapper.readValue(resource.getInputStream(), String[].class);

            return Arrays.asList(champsArray);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FetchingKeywordsException("Failed to load keywords");
        }
    }
}
