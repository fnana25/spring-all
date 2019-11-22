package com.example.thymeleafviewdemo.service;

import com.example.thymeleafviewdemo.model.Coffee;
import com.example.thymeleafviewdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/22
 * <description>：TODO
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public Coffee getCoffee(Long id){
        return coffeeRepository.getOne(id);
    }

    public Coffee getCoffee(String name){
        return coffeeRepository.findByName(name);
    }

    public List<Coffee> getCoffeeByName(List<String> names){
        return coffeeRepository.findByNameInOrderById(names);
    }

    @Cacheable
    public List<Coffee> getAll(){
        return coffeeRepository.findAll();
    }

    public Coffee saveCoffee(String name, Money price){
        Coffee coffee = Coffee.builder().name(name).price(price).build();
        return coffeeRepository.save(coffee);
    }

}