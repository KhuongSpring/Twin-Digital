package org.example.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@SpringBootApplication(scanBasePackages = "org.example.carservice")
public class CarServiceApplication {

    public static void main(String[] args) {
        Environment env = SpringApplication.run(CarServiceApplication.class, args).getEnvironment();
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
