package com.example.ratelimiterwaiterservice.controller;

import com.example.ratelimiterwaiterservice.controller.request.NewOrderRequest;
import com.example.ratelimiterwaiterservice.model.Coffee;
import com.example.ratelimiterwaiterservice.model.CoffeeOrder;
import com.example.ratelimiterwaiterservice.service.CoffeeOrderService;
import com.example.ratelimiterwaiterservice.service.CoffeeService;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/20
 * <description>：TODO
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class CoffeeOrderController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private CoffeeService coffeeService;

    private RateLimiter rateLimiter;

    public CoffeeOrderController(RateLimiterRegistry registry) {
        rateLimiter = registry.rateLimiter("order");
    }

    @GetMapping(params = "/{id}")
    public CoffeeOrder getOrder(@PathVariable Long id) {

        CoffeeOrder order = null;
        try {
            order = rateLimiter.executeSupplier(() -> coffeeOrderService.getOrder(id));
            log.info("Get order:{}", order);
        } catch (RequestNotPermitted e) {
            log.warn("Request not permitted,{}", e.getMessage());
        }
        return order;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @io.github.resilience4j.ratelimiter.annotation.RateLimiter(name = "order")
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CoffeeOrder createOrder(@RequestBody NewOrderRequest request) {
        List<Coffee> list = coffeeService.getBynames(request.getItems());
        CoffeeOrder order = coffeeOrderService.createOrder(request.getCustomer(), list);
        log.info("Order Created:{}", order);
        return order;
    }
}