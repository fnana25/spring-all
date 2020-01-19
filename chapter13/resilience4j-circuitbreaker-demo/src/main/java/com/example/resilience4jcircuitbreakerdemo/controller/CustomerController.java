package com.example.resilience4jcircuitbreakerdemo.controller;

import com.example.resilience4jcircuitbreakerdemo.integration.CoffeeOrderService;
import com.example.resilience4jcircuitbreakerdemo.integration.CoffeeService;
import com.example.resilience4jcircuitbreakerdemo.model.Coffee;
import com.example.resilience4jcircuitbreakerdemo.model.CoffeeOrder;
import com.example.resilience4jcircuitbreakerdemo.model.NewOrderRequest;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/19
 * <description>：TODO
 */
@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    //如果没有@EnableFeignClients注解，service不能注入
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeOrderService coffeeOrderService;
    private CircuitBreaker circuitBreaker;

    public CustomerController(CircuitBreakerRegistry registry) {
        circuitBreaker = registry.circuitBreaker("menu");
    }

    @GetMapping("/menu")
    public List<Coffee> menu() {

        return Try.ofSupplier(CircuitBreaker.decorateSupplier(circuitBreaker, () -> coffeeService.getAll()))
                .recover(CircuitBreakerOpenException.class, Collections.emptyList())
                .get();
    }

    @PostMapping(path = "/order")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "order")
    public CoffeeOrder createOrder() {
        NewOrderRequest request = NewOrderRequest.builder().customer("nana").items(Arrays.asList("capuccino")).build();
        CoffeeOrder order = coffeeOrderService.createOrder(request);
        log.info("Create Order {}", order);
        return order;
    }
}