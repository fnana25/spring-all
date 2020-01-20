package com.example.ratelimiterwaiterservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@EnableCaching
@EnableJpaRepositories
@EnableDiscoveryClient
@SpringBootApplication
public class RatelimiterWaiterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatelimiterWaiterServiceApplication.class, args);
	}

}
