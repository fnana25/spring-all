package com.example.simpleresttemplatedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@Slf4j
@SpringBootApplication
public class SimpleResttemplateDemoApplication implements ApplicationRunner {

	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(SimpleResttemplateDemoApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/coffee/{id}").build(1);
		ResponseEntity<Coffee> responseEntity = restTemplate.getForEntity(uri,Coffee.class);
		log.info("Response status: {},Response Headers: {}",responseEntity.getStatusCode(),responseEntity.getHeaders());
		log.info("Response Body: {}",responseEntity.getBody());

		String coffeeUri = "http://localhost:8080/coffee/";
		Coffee coffee = Coffee.builder()
				.name("America")
				.price(BigDecimal.valueOf(25.00))
				.build();
		ResponseEntity<Coffee> response = restTemplate.postForEntity(coffeeUri,coffee,Coffee.class);
		log.info("New Coffee: {}",response);
		String s = restTemplate.getForObject(coffeeUri,String.class);
		log.info("String : {}",s);
	}
}
