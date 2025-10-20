package org.example.carservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "static-spec-service")
@Getter
@Setter
public class StaticSpecServiceConfig {
  private String url;
}
