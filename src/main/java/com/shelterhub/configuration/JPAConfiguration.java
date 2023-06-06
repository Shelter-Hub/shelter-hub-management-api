package com.shelterhub.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.shelterhub")
@EnableJpaRepositories(basePackages = "com.shelterhub")
public class JPAConfiguration {
}
