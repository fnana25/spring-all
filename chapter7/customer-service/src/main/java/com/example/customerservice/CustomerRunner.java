package com.example.customerservice;

import com.example.customerservice.model.Coffee;
import com.example.customerservice.model.CoffeeOrder;
import com.example.customerservice.model.NewOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
 * @create 2019/12/23
 * <description>：TODO
 */
@Slf4j
@Component
public class CustomerRunner implements ApplicationRunner {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        readMenu();
        Long id = createOrder();
        findOrder(id);
    }

    private void readMenu() {
        ParameterizedTypeReference<List<Coffee>> coffees = new ParameterizedTypeReference<List<Coffee>>() {
        };
        ResponseEntity<List<Coffee>> response = restTemplate.exchange("http://localhost:8080/coffee", HttpMethod.GET, null, coffees);
        response.getBody().forEach(e -> log.info("Coffee:{}", e));
    }

    private Long createOrder() {
        NewOrderRequest req = NewOrderRequest.builder().customer("nana").items(Arrays.asList("mocha")).build();
        RequestEntity<NewOrderRequest> requestEntity = RequestEntity.post(UriComponentsBuilder.fromUriString("http://localhost:8080/order").build().toUri())
                .body(req);
        ResponseEntity<CoffeeOrder> responseEntity = restTemplate.exchange(requestEntity, CoffeeOrder.class);
        log.info("Order Status Code:{}", responseEntity.getStatusCode());
        Long id = responseEntity.getBody().getId();
        log.info("Order Id:{}", id);
        return id;
    }

    private CoffeeOrder findOrder(Long id) {
        CoffeeOrder order = restTemplate.getForObject("http://localhost:8080/order/{id}", CoffeeOrder.class, id);
        return order;
    }
}