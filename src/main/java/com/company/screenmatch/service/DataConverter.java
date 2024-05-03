package com.company.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IJsonDataConverter {

    //instancia de la clase ObjectMapper de la libreria Jackson
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public <T> T dataJsonToClass(String json, Class<T> parameterClass) {
        try {
            return objectMapper.readValue(json, parameterClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
