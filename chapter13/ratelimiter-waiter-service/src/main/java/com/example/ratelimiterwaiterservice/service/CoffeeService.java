package com.example.ratelimiterwaiterservice.service;

import com.example.ratelimiterwaiterservice.model.Coffee;
import com.example.ratelimiterwaiterservice.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/20
 * <description>：TODO
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public Coffee saveCoffee(String name, Money price) {
        return coffeeRepository.save(Coffee.builder().name(name).price(price).build());
    }

    @Cacheable
    public List<Coffee> getAll() {
        return coffeeRepository.findAll(Sort.by("id"));
    }

    public long getCoffeeCount() {
        return coffeeRepository.count();
    }

    public Coffee getById(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee getByName(String name) {
        return coffeeRepository.findByName(name);
    }

    public List<Coffee> getBynames(List<String> names) {
        return coffeeRepository.findByNameInOrderById(names);
    }
}