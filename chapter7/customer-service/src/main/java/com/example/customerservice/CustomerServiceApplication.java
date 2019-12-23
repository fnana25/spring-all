package com.example.customerservice;

import com.example.customerservice.support.CustomConnectionKeepAliveStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class CustomerServiceApplication {

	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		new SpringApplicationBuilder(CustomerServiceApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory requestFactory(){

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
		connectionManager.setMaxTotal(200);
		connectionManager.setDefaultMaxPerRoute(20);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connectionManager)
				.evictIdleConnections(30,TimeUnit.SECONDS)
				.disableAutomaticRetries()
				.setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
				.build();
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){

		return builder.setConnectTimeout(Duration.ofMillis(200))
				.setReadTimeout(Duration.ofMillis(100))
				.requestFactory(this::requestFactory)
				.build();
	}
}
