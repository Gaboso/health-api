package com.github.gaboso.healthcareapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class HealthCareApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCareApiApplication.class, args);
	}

}
