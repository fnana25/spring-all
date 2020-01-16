package com.example.zkwaiterservice;

import com.example.zkwaiterservice.model.Coffee;
import com.example.zkwaiterservice.model.CoffeeOrder;
import com.example.zkwaiterservice.model.NewOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/16
 * <description>：TODO
 */
@Slf4j
@Component
public class CustomerRunner implements ApplicationRunner {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) {
        showServiceInstance();
        readMenu();
        Long id = orderCoffee();
        queryOrder(id);
    }

    private void showServiceInstance() {
        log.info("DiscoveryClient class:{}", discoveryClient.getClass());
        discoveryClient.getInstances("waiter-service").forEach(e -> log.info("Host:{},Port{}", e.getHost(), e.getPort()));
    }

    private void readMenu() {
        ParameterizedTypeReference<List<Coffee>> ptr =
                new ParameterizedTypeReference<List<Coffee>>() {
                };
        ResponseEntity<List<Coffee>> list = restTemplate
                .exchange("http://waiter-service/coffee/", HttpMethod.GET, null, ptr);
        list.getBody().forEach(c -> log.info("Coffee: {}", c));
    }

    private Long orderCoffee() {
        NewOrderRequest.NewOrderRequestBuilder builder = NewOrderRequest.builder();
        builder.customer("Li Lei");
        builder.items(Arrays.asList("capuccino"));
        NewOrderRequest orderRequest = builder
                .build();
        RequestEntity<NewOrderRequest> request = RequestEntity
                .post(UriComponentsBuilder.fromUriString("http://waiter-service/order/").build().toUri())
                .body(orderRequest);
        ResponseEntity<CoffeeOrder> response = restTemplate.exchange(request, CoffeeOrder.class);
        log.info("Order Request Status Code: {}", response.getStatusCode());
        Long id = response.getBody().getId();
        log.info("Order ID: {}", id);
        return id;
    }

    private void queryOrder(Long id) {
        CoffeeOrder order = restTemplate
                .getForObject("http://waiter-service/order/{id}", CoffeeOrder.class, id);
        log.info("Order: {}", order);
    }
}