package com.example.eurekawaiterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@EnableJpaRepositories
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaWaiterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaWaiterServiceApplication.class, args);
	}

}
