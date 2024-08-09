package com.hq.siiutest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hq.siiutest.models.SiiuTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    private static final String APP_CONFIG_DIR = System.getProperty("user.dir");
    private static final  String JSON_NAME = "tests.json";
    private static final String JSON_TEST_PATH = APP_CONFIG_DIR + File.separator + JSON_NAME;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<SiiuTest> getSiiuTests() {

        try {
            File json = new File(JSON_TEST_PATH);
            return objectMapper.readValue(json, new TypeReference<List<SiiuTest>>() {
            });
        } catch (JsonProcessingException e) {
            logger.error("Error procesando el archivo JSON: " + JSON_NAME, e);
        } catch (IOException e) {
            logger.error("No existe o no se pudo abrir el archivo: " + JSON_NAME, e);
        }

        return null;
    }

}
