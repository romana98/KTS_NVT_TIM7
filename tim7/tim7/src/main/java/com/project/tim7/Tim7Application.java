package com.project.tim7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Tim7Application {

	public static void main(String[] args) {
		SpringApplication.run(Tim7Application.class, args);
	}

}
