package com.example.redisrepositorydemo.service;

import com.example.redisrepositorydemo.model.Coffee;
import com.example.redisrepositorydemo.model.CoffeeOrder;
import com.example.redisrepositorydemo.model.OrderState;
import com.example.redisrepositorydemo.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * @uthor : fengna
 * @create 2019/11/15
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public CoffeeOrder createOrder(String customer, Coffee...coffees){
        CoffeeOrder order = CoffeeOrder.builder().customer(customer).items(Arrays.asList(coffees)).state(OrderState.INIT).build();
        CoffeeOrder saved = coffeeOrderRepository.save(order);
        log.info("New Order : {} Saved",saved);
        return saved;
    }

    public boolean updateOrderState(CoffeeOrder order,OrderState state){
        if(state.compareTo(order.getState())<=0){
            log.info("Wrong Order State {},{}",order,state);
            return false;
        }
        order.setState(state);
        coffeeOrderRepository.save(order);
        log.info("Update Order {}",order);
        return true;
    }
}