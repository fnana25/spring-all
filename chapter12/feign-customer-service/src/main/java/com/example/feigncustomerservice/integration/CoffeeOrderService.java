package com.example.feigncustomerservice.integration;

import com.example.feigncustomerservice.model.CoffeeOrder;
import com.example.feigncustomerservice.model.NewOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "customer-service",contextId = "orderService")
public interface CoffeeOrderService {
    @GetMapping("/order/{id}")
    CoffeeOrder getOrder(@PathVariable("id") Long id);

    @PostMapping(path = "/order/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CoffeeOrder create(@RequestBody NewOrderRequest newOrder);
}
