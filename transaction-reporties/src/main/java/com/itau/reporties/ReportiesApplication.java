package com.itau.reporties;

import com.itau.security.property.JwtConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"com.itau.security.model"})
@EnableJpaRepositories({"com.itau.security.model.repository"})
@EnableConfigurationProperties(value = JwtConfiguration.class)
@ComponentScan("com.itau.security")
public class ReportiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportiesApplication.class, args);
	}

}
