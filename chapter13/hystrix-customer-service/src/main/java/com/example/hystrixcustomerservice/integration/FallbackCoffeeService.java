package com.example.hystrixcustomerservice.integration;

import com.example.hystrixcustomerservice.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/17
 * <description>：TODO
 */
@Slf4j
@Component
public class FallbackCoffeeService implements CoffeeService{

    @Override
    public List<Coffee> getAll() {
        log.info("Fallback method getAll...");
        return Collections.emptyList();
    }

    @Override
    public Coffee getById(Long id) {
        return null;
    }

    @Override
    public Coffee getByName(String name) {
        return null;
    }
}