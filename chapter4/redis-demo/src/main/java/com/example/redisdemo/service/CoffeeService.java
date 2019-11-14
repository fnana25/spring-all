package com.example.redisdemo.service;

import com.example.redisdemo.model.Coffee;
import com.example.redisdemo.repostories.CoffeeRepostory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;
/**
 * @uthor : fengna
 * @create 2019/11/13
 * <description>：TODO
 */
@Slf4j
@Service
public class CoffeeService {

    private static final String CACHE = "springbucks-coffee";

    @Autowired
    private CoffeeRepostory coffeeRepostory;

    @Autowired
    private RedisTemplate<String, Coffee> redisTemplate;

    public List<Coffee> findAll(){
        return coffeeRepostory.findAll();
    }

    public Optional<Coffee> findOneCoffee(String name){
        HashOperations<String,String,Coffee> hashOperations = redisTemplate.opsForHash();
        if(redisTemplate.hasKey(CACHE) && hashOperations.hasKey(CACHE,name)){
            log.info("Get coffee {} from redis",name);
            return Optional.of(hashOperations.get(CACHE,name));
        }

        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name",exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepostory.findOne(Example.of(Coffee.builder().name(name).build(),matcher));
        log.info("Coffee found {}",coffee);
        if(coffee.isPresent()){
            log.info("put coffee {} into redis",coffee);
            hashOperations.put(CACHE,name,coffee.get());
            redisTemplate.expire(CACHE,10L, TimeUnit.SECONDS);
        }
        return coffee;
    }
}