package com.example.indicatordemo.support;

import com.example.indicatordemo.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @uthor : fengna
 * @create 2020/1/2
 * <description>：TODO
 */
@Component
public class CoffeeIndicator implements HealthIndicator {

    @Autowired
    private CoffeeService coffeeService;

    @Override
    public Health health() {
        Health health;
        long count = coffeeService.getCoffeeCount();
        if (count > 0) {
            health = Health.up()
                    .withDetail("count", count)
                    .withDetail("message", "we have enough coffee")
                    .build();
        } else {
            health = Health.down()
                    .withDetail("count", 0)
                    .withDetail("message", "we are out of coffee")
                    .build();
        }
        return health;
    }
}