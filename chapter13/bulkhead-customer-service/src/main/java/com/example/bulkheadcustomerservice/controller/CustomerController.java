package com.example.bulkheadcustomerservice.controller;

import com.example.bulkheadcustomerservice.integration.CoffeeOrderService;
import com.example.bulkheadcustomerservice.integration.CoffeeService;
import com.example.bulkheadcustomerservice.model.Coffee;
import com.example.bulkheadcustomerservice.model.CoffeeOrder;
import com.example.bulkheadcustomerservice.model.NewOrderRequest;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
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
 * @create 2020/1/20
 * <description>：TODO
 */
@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    private CircuitBreaker circuitBreaker;
    private Bulkhead bulkhead;

    public CustomerController(CircuitBreakerRegistry circuitBreakerRegistry, BulkheadRegistry bulkheadRegistry) {
        circuitBreaker = circuitBreakerRegistry.circuitBreaker("menu");
        bulkhead = bulkheadRegistry.bulkhead("menu");
    }

    @GetMapping("/")
    public List<Coffee> menu() {
        return Try.ofSupplier(
                Bulkhead.decorateSupplier(bulkhead,
                        CircuitBreaker.decorateSupplier(circuitBreaker,
                                () -> coffeeService.getAll()
                        )
                )
        ).recover(CircuitBreakerOpenException.class, Collections.emptyList())
                .recover(BulkheadFullException.class, Collections.emptyList())
                .get();
    }

    @PostMapping(path = "/order")
    @io.github.resilience4j.bulkhead.annotation.Bulkhead(name = "order")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "order")
    public CoffeeOrder createOrder() {
        NewOrderRequest request = NewOrderRequest.builder().customer("nana").items(Arrays.asList("capuccino")).build();
        CoffeeOrder order = coffeeOrderService.createOrder(request);
        log.info("Ordre Created:{}", order);
        return order;
    }
}