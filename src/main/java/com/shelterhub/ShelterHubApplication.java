package com.shelterhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.shelterhub")
@EnableJpaRepositories(basePackages = "com.shelterhub")
public class ShelterHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShelterHubApplication.class, args);
	}

}

