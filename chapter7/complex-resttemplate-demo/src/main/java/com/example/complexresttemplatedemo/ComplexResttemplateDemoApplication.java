package com.example.complexresttemplatedemo;

import com.example.complexresttemplatedemo.model.Coffee;
import com.sun.jndi.toolkit.url.Uri;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@SpringBootApplication
public class ComplexResttemplateDemoApplication implements ApplicationRunner {

    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(ComplexResttemplateDemoApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //select
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/coffee/?name={name}").build("mocha");
        RequestEntity<Void> req = RequestEntity.get(uri).accept(MediaType.APPLICATION_XML).build();
        ResponseEntity<String> response = restTemplate.exchange(req, String.class);
        log.info("Response Status:{}  Response Headers:{}", response.getStatusCode(), response.getHeaders().toString());
        log.info("Coffee:{}", response.getBody());

        //add coffee
        String coffeeUrl = "http://localhost:8080/coffee";
        Coffee coffee = Coffee.builder().name("Americano").price(Money.of(CurrencyUnit.of("CNY"), 25.00)).build();
        Coffee res = restTemplate.postForObject(coffeeUrl, coffee, Coffee.class);
        log.info("Create Coffee:{}", res);

        //解析泛型
        ParameterizedTypeReference<List<Coffee>> coffeeList = new ParameterizedTypeReference<List<Coffee>>() {};
        ResponseEntity<List<Coffee>> coffeesResp = restTemplate.exchange(coffeeUrl, HttpMethod.GET, null, coffeeList);
        coffeesResp.getBody().forEach(e -> log.info("Coffee:{}", e));
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
