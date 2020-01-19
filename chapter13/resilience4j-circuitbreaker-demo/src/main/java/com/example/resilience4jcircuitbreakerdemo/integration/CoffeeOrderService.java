package com.example.resilience4jcircuitbreakerdemo.integration;

import com.example.resilience4jcircuitbreakerdemo.model.CoffeeOrder;
import com.example.resilience4jcircuitbreakerdemo.model.NewOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "waiter-service", contextId = "coffeeOrder")
public interface CoffeeOrderService {

    @GetMapping(path = "/order/{id}")
    CoffeeOrder getById(@PathVariable Long id);

    @PostMapping(path = "/order/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CoffeeOrder createOrder(@RequestBody NewOrderRequest request);
}
