package com.example.simplecontrollerdemo.controller;

import com.example.simplecontrollerdemo.controller.request.NewOrderRequest;
import com.example.simplecontrollerdemo.model.Coffee;
import com.example.simplecontrollerdemo.model.CoffeeOrder;
import com.example.simplecontrollerdemo.service.CoffeeOrderService;
import com.example.simplecontrollerdemo.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * @uthor : fengna
 * @create 2019/11/19
 * <description>：TODO
 */
@RestController
@RequestMapping("/order")
public class CoffeeOrderController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private CoffeeService coffeeService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder createOrder(@RequestBody NewOrderRequest req){
        Coffee[] coffees = coffeeService.findAllByNames(req.getItems()).toArray(new Coffee[]{});
        CoffeeOrder order = coffeeOrderService.createOrder(req.getCustomer(),coffees);
        return order;
    }
}