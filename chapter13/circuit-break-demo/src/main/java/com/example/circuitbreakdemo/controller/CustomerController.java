package com.example.circuitbreakdemo.controller;

import com.example.circuitbreakdemo.integration.CoffeeOrderService;
import com.example.circuitbreakdemo.integration.CoffeeService;
import com.example.circuitbreakdemo.model.Coffee;
import com.example.circuitbreakdemo.model.CoffeeOrder;
import com.example.circuitbreakdemo.model.NewOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/17
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

    @GetMapping("/menu")
    public List<Coffee> menu() {
        List<Coffee> list = coffeeService.getAll();
        log.info("Get All Coffee {}", list.size());
        return list;
    }

    public CoffeeOrder createOrder() {
        NewOrderRequest request = NewOrderRequest.builder().customer("nana").items(Arrays.asList("capuccino")).build();
        CoffeeOrder order = coffeeOrderService.createOrder(request);
        log.info("Order created:{}", order);
        return order;
    }
}