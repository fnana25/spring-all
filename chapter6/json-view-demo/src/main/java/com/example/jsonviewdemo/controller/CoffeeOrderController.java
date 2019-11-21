package com.example.jsonviewdemo.controller;

import com.example.jsonviewdemo.controller.request.NewOrderReq;
import com.example.jsonviewdemo.model.Coffee;
import com.example.jsonviewdemo.model.CoffeeOrder;
import com.example.jsonviewdemo.service.CoffeeOrderService;
import com.example.jsonviewdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/21
 * <description>：TODO
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class CoffeeOrderController {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @GetMapping(path = "/{id}")
    public CoffeeOrder findById(@PathVariable Long id) {
        return coffeeOrderService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CoffeeOrder createOrder(@RequestBody @Valid NewOrderReq req) {
        log.info("Order Create...");
        List<Coffee> coffees = coffeeService.findByNames(req.getItems());
        return coffeeOrderService.createOrder(req.getCustomer(), coffees.toArray(new Coffee[]{}));
    }
}