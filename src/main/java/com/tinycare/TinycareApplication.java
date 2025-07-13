package com.tinycare;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TinycareApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinycareApplication.class, args);
	}
	@PostConstruct
	public void init() {
		// This should print your PostgreSQL URL if application.properties is loaded
		System.out.println("✅ spring.datasource.url = " + System.getProperty("spring.datasource.url"));
	}
}
