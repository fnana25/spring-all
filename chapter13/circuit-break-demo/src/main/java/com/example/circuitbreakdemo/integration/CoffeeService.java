package com.example.circuitbreakdemo.integration;


import com.example.circuitbreakdemo.model.Coffee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "waiter-service",contextId = "coffee",path = "/coffee")
public interface CoffeeService {

    @GetMapping(path = "/",params = "!name")
    List<Coffee> getAll();

    @GetMapping(path = "/{id}")
    Coffee getById(@PathVariable Long id);

    @GetMapping(path = "/",params = "name")
    Coffee getByName(String name);
}
