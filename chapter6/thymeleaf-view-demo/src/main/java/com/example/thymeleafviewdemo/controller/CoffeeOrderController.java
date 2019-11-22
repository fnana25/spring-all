package com.example.thymeleafviewdemo.controller;

import com.example.thymeleafviewdemo.controller.request.NewOrderRequest;
import com.example.thymeleafviewdemo.model.Coffee;
import com.example.thymeleafviewdemo.model.CoffeeOrder;
import com.example.thymeleafviewdemo.service.CoffeeOrderService;
import com.example.thymeleafviewdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/22
 * <description>：TODO
 */
@Slf4j
@Controller
@RequestMapping("/order")
public class CoffeeOrderController {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @ResponseBody
    @GetMapping(path = "/{id}")
    public CoffeeOrder findById(@PathVariable("id") Long id){
        return coffeeOrderService.findById(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "",consumes = MediaType.APPLICATION_JSON_VALUE)
    public CoffeeOrder addOrder(@RequestBody @Valid NewOrderRequest req){
        log.info("Order Add...");
        List<Coffee> coffees = coffeeService.getCoffeeByName(req.getItems());
        CoffeeOrder order = coffeeOrderService.createOrder(req.getCustomer(),coffees.toArray(new Coffee[]{}));
        log.info("Order saved:{}",order);
        return order;
    }

    @PostMapping(path = "",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addOrder(@Valid NewOrderRequest req, BindingResult result, ModelMap map){

        if(result.hasErrors()){
            log.warn("Binding result:{}",result);
            map.addAttribute("message",result.toString());
            return "create-order-form";
        }
        log.info("Receive New order...");
        List<Coffee> coffees = coffeeService.getCoffeeByName(req.getItems());
        CoffeeOrder order = coffeeOrderService.createOrder(req.getCustomer(),coffees.toArray(new Coffee[]{}));
        log.info("Order saved:{}",order);
        return "redirect:/order/" + order.getId();
    }

    @ModelAttribute
    public List<Coffee> coffeeList(){
        return coffeeService.getAll();
    }

    @GetMapping(path = "")
    public String showCreateForm(){
        return "create-order-form";
    }
}