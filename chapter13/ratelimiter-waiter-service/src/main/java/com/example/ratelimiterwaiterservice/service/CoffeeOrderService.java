package com.example.ratelimiterwaiterservice.service;

import com.example.ratelimiterwaiterservice.model.Coffee;
import com.example.ratelimiterwaiterservice.model.CoffeeOrder;
import com.example.ratelimiterwaiterservice.model.OrderState;
import com.example.ratelimiterwaiterservice.repository.CoffeeOrderRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/20
 * <description>：TODO
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService implements MeterBinder {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    private Counter orderCount;

    public CoffeeOrder getOrder(Long id) {
        return coffeeOrderRepository.getOne(id);
    }

    public CoffeeOrder createOrder(String customer, List<Coffee> items) {
        CoffeeOrder order = coffeeOrderRepository.save(
                CoffeeOrder.builder().customer(customer).items(items).state(OrderState.INIT).build()
        );
        log.info("Order created:{}",order);
        return order;
    }

    public boolean updateState(CoffeeOrder order,OrderState state){
        if(state.compareTo(order.getState())<=0){
            log.error("Error order state:{}",state);
            return false;
        }
        order.setState(state);
        coffeeOrderRepository.save(order);
        return true;
    }


    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.orderCount = meterRegistry.counter("order.count");
    }
}