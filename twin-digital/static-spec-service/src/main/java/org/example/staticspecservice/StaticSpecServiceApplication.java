package org.example.staticspecservice;

import lombok.RequiredArgsConstructor;
import org.example.staticspecservice.config.DotenvInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@SpringBootApplication(scanBasePackages = "org.example.staticspecservice")
public class StaticSpecServiceApplication {

    public static void main(String[] args) {
        Environment env = new SpringApplicationBuilder(StaticSpecServiceApplication.class)
                .initializers(new DotenvInitializer())
                .run(args)
                .getEnvironment();

        String appName = env.getProperty("spring.application.name");
        if (appName != null) {
            appName = appName.toUpperCase();
        }
        String port = env.getProperty("server.port");
        log.info("-------------------------START " + appName
                + " Application------------------------------");
        log.info("   Application         : " + appName);
        log.info("   Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
        log.info("-------------------------START SUCCESS " + appName
                + " Application------------------------------");
    }
}