package com.example.spring_practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;

@SpringBootApplication
@EnableR2dbcRepositories(
		value = "com.example.spring_practice",
		repositoryBaseClass = SimpleR2dbcRepository.class)
public class SpringPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPracticeApplication.class, args);
	}

}
