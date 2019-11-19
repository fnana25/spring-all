package com.example.simplecontrollerdemo.service;

import com.example.simplecontrollerdemo.model.Coffee;
import com.example.simplecontrollerdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/19
 * <description>：TODO
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "coffee")
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Cacheable
    public List<Coffee> findAllCoffee(){
        return coffeeRepository.findAll();
    }

    public List<Coffee> findAllByNames(List<String> list){
        return coffeeRepository.findByNameInOrderById(list);
    }


}