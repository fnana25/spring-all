package com.example.redisrepositorydemo.service;

import com.example.redisrepositorydemo.model.Coffee;
import com.example.redisrepositorydemo.model.CoffeeCache;
import com.example.redisrepositorydemo.repository.CoffeeCacheRepository;
import com.example.redisrepositorydemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

/**
 * @uthor : fengna
 * @create 2019/11/15
 */
@Slf4j
@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeCacheRepository cacheRepository;

    public List<Coffee> findAllCoffee() {
        return coffeeRepository.findAll();
    }

    public Optional<Coffee> findSimpleCoffeeFromCache(String name) {
        Optional<CoffeeCache> coffeeCache = cacheRepository.findOneByName(name);
        if (coffeeCache.isPresent()) {
            CoffeeCache cacheCoffee = coffeeCache.get();
            Coffee coffee = Coffee.builder().name(name).price(cacheCoffee.getPrice()).build();
            return Optional.of(coffee);
        } else {
            Optional<Coffee> optionalCoffee = findOneCoffee(name);
            optionalCoffee.ifPresent(c -> {
                CoffeeCache cache = CoffeeCache.builder().name(name).price(c.getPrice()).build();
                cacheRepository.save(cache);
            });
            log.info("Save Coffee {} to redis", coffeeCache);
            return optionalCoffee;
        }
    }

    public Optional<Coffee> findOneCoffee(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found : {}", coffee);
        return coffee;
    }
}