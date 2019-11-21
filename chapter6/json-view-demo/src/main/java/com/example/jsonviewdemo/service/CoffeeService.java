package com.example.jsonviewdemo.service;

import com.example.jsonviewdemo.model.Coffee;
import com.example.jsonviewdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/21
 * <description>：TODO
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "coffee")
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public Coffee getByName(String name){
        return coffeeRepository.findByName(name);
    }

    @Cacheable
    public List<Coffee> findAll(){
        return coffeeRepository.findAll();
    }

    public List<Coffee> findByNames(List<String> names){
        return coffeeRepository.findByNameInOrderById(names);
    }

    public Coffee findById(Long id){
        return coffeeRepository.getOne(id);
    }

    public Coffee saveCoffee(String name, Money price){
        Coffee coffee = Coffee.builder().name(name).price(price).build();
        return coffeeRepository.save(coffee);
    }
}