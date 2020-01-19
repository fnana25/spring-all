package com.example.resilience4jcircuitbreakerdemo.integration;

import com.example.resilience4jcircuitbreakerdemo.model.Coffee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "customer",contextId = "coffee",path = "coffee")
public interface CoffeeService {

    @GetMapping(path = "/",params = "!name")
    List<Coffee> getAll();

    @GetMapping(path = "/{id}")
    Coffee getById(@PathVariable Long id);

    @GetMapping(path = "/",params = "name")
    Coffee getByName(String name);

}
