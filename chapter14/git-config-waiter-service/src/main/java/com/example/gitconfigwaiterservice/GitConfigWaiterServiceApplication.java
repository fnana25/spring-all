package com.example.gitconfigwaiterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class GitConfigWaiterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitConfigWaiterServiceApplication.class, args);
	}

}
