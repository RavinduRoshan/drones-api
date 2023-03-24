package com.musalasoft.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.musalasoft.exception.DroneApiException;
import org.springframework.http.HttpStatus;

public class JsonConverter {
    private JsonConverter(){}

    public static String toJson(Object object) throws DroneApiException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new DroneApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
