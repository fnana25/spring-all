package com.example.redisrepositorydemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

/**
 * @uthor : fengna
 * @create 2019/11/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "springbucks-coffee",timeToLive = 60)
public class CoffeeCache {

    @Id
    private Long id;

    @Indexed
    private String name;

    private Money price;
}