package com.example.continuous_data_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
public class ContinuousDataServiceApplication {

	public static void main(String[] args) {
		Environment env = SpringApplication.run(ContinuousDataServiceApplication.class, args).getEnvironment();
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
