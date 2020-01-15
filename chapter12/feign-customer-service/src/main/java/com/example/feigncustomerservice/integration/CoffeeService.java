package com.example.feigncustomerservice.integration;

import com.example.feigncustomerservice.model.Coffee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 不要在接口上加@RequestMapping
@FeignClient(value = "customer-service",contextId = "coffeeService",path = "/coffee")
public interface CoffeeService {

    @GetMapping(path = "/", params = "!name")
    List<Coffee> getAll();

    @GetMapping("/{id}")
    Coffee getById(@PathVariable Long id);

    @GetMapping(path = "/", params = "name")
    Coffee getByName(@RequestParam String name);
}
