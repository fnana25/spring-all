package com.example.exceptiondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@SpringBootApplication
@EnableJpaRepositories
public class ExceptionDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExceptionDemoApplication.class, args);
	}

}
