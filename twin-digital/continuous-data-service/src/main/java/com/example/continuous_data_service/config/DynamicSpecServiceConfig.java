package com.example.continuous_data_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dynamic-spec-service")
@Getter
@Setter
public class DynamicSpecServiceConfig {
    private String url;
}
