package com.example.week6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Week6Application {

	public static void main(String[] args) {
		SpringApplication.run(Week6Application.class, args);
	}

}
