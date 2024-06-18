package com.example.spring_practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableJpaRepositories(
		value = "com.example.spring_practice.repository",
		repositoryBaseClass = JpaRepository.class
)
@EnableR2dbcRepositories(
		value = "com.example.spring_practice.reactive_repo",
		repositoryBaseClass = R2dbcRepository.class)
public class SpringPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPracticeApplication.class, args);
	}

}
