package org.example.staticspecservice.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
public class DotenvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        try {
            Map<String, Object> envMap = loadEnvFile();

            MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
            propertySources.addFirst(new MapPropertySource("dotenv", envMap));

            System.out.println("Loaded " + envMap.size() + " properties from .env");

        } catch (Exception e) {
            System.err.println("Failed to load .env: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Map<String, Object> loadEnvFile() throws IOException {
        Map<String, Object> envMap = new HashMap<>();
        Path envFile = Paths.get("D:\\Twin-Digital\\twin-digital\\static-spec-service\\.env");

        if (!Files.exists(envFile)) {
            System.out.println(".env file not found at: " + envFile.toAbsolutePath());
            return envMap;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(envFile.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                int equalIndex = line.indexOf('=');
                if (equalIndex > 0) {
                    String key = line.substring(0, equalIndex).trim();
                    String value = line.substring(equalIndex + 1).trim();
                    envMap.put(key, value);
                }
            }
        }

        return envMap;
    }
}