package com.example.hystrixcustomerservice.controller;

import com.example.hystrixcustomerservice.integration.CoffeeOrderService;
import com.example.hystrixcustomerservice.integration.CoffeeService;
import com.example.hystrixcustomerservice.model.Coffee;
import com.example.hystrixcustomerservice.model.CoffeeOrder;
import com.example.hystrixcustomerservice.model.NewOrderRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public List<Coffee> menu(){
        List<Coffee> list = coffeeService.getAll();
        log.info("Read menu {} coffee",list.size());
        return list;
    }

    @PostMapping(path = "/order")
    @HystrixCommand(fallbackMethod = "fallbackCreateOrder" )
    public CoffeeOrder createOrder(){
        NewOrderRequest orderRequest = NewOrderRequest.builder().customer("nana").items(Arrays.asList("capuccino")).build();
        CoffeeOrder order = coffeeOrderService.create(orderRequest);
        log.info("Create order {}",order);
        return order;
    }

    public CoffeeOrder fallbackCreateOrder(){
        log.info("Call fallback method");
        return null;
    }
}