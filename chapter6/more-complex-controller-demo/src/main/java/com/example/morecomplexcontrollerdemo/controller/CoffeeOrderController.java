package com.example.morecomplexcontrollerdemo.controller;

import com.example.morecomplexcontrollerdemo.controller.request.NewOrderRequest;
import com.example.morecomplexcontrollerdemo.model.Coffee;
import com.example.morecomplexcontrollerdemo.model.CoffeeOrder;
import com.example.morecomplexcontrollerdemo.service.CoffeeOrderService;
import com.example.morecomplexcontrollerdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @uthor : fengna
 * @create 2019/11/20
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
    public CoffeeOrder findOrder(@PathVariable Long id){
        return coffeeOrderService.getById(id).orElseGet(CoffeeOrder::new);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CoffeeOrder createOrder(@RequestBody NewOrderRequest req){
        log.info("Save new Order:{}",req);
        Coffee[] coffees = coffeeService.getByNames(req.getNames()).toArray(new Coffee[]{});
        return coffeeOrderService.createOrder(req.getCustomer(),coffees);
    }
}