package org.example.staticspecservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarSpecFileReader {

    private final ObjectMapper objectMapper;
    private final Map<String, Map<String, Object>> carSpecMap = new HashMap<>();

    public Map<String, Object> readCarSpecFromFile(String carModelName) {
        try {

            String fileName = "vinfast_" + carModelName.toLowerCase() + "_spec.json";
            String resourcePath = "/data/" + fileName;

            try(InputStream inputStream = getClass().getResourceAsStream(resourcePath)){
                if (inputStream == null) {
                    throw new RuntimeException("Car specification file not found: " + fileName);
                }

                Map<String, Object> specData = objectMapper.readValue(inputStream, Map.class);
                carSpecMap.put(carModelName, specData);

                log.info("Successfully loaded car specification for model: {}", carModelName);
                return specData;
            }
        } catch (Exception e) {
            log.error("Error reading car specification for model: {}", carModelName, e);
            throw new RuntimeException("Failed to load car specification for model: " + carModelName, e);
        }
    }

}
