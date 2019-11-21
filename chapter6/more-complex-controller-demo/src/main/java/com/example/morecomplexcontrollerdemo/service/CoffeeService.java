package com.example.morecomplexcontrollerdemo.service;

import com.example.morecomplexcontrollerdemo.model.Coffee;
import com.example.morecomplexcontrollerdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @uthor : fengna
 * @create 2019/11/20
 * <description>：TODO
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public Coffee getByName(String name){
        return coffeeRepository.findByName(name);
    }

    public Coffee getById(Long id){
        return coffeeRepository.getOne(id);
    }

    public List<Coffee> getByNames(List<String> list){
        return coffeeRepository.findByNameInOrderById(list);
    }

    @Cacheable
    public List<Coffee> findAllCoffee(){
        return coffeeRepository.findAll(Sort.by("id"));
    }

    public Coffee saveCoffee(String name, Money price){
        Coffee coffee = Coffee.builder().name(name).price(price).build();
        return coffeeRepository.save(coffee);
    }

}