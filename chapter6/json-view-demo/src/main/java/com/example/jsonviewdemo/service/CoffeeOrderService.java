package com.example.jsonviewdemo.service;

import com.example.jsonviewdemo.model.Coffee;
import com.example.jsonviewdemo.model.CoffeeOrder;
import com.example.jsonviewdemo.model.OrderState;
import com.example.jsonviewdemo.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * @uthor : fengna
 * @create 2019/11/21
 * <description>：TODO
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public CoffeeOrder findById(Long id){
        return coffeeOrderRepository.getOne(id);
    }

    public CoffeeOrder createOrder(String customer, Coffee...coffees){
        CoffeeOrder order = CoffeeOrder.builder().customer(customer).items(Arrays.asList(coffees)).state(OrderState.INIT).build();
        CoffeeOrder saved = coffeeOrderRepository.save(order);
        log.info("Order saved:{}",saved);
        return saved;
    }

    public boolean updateOrder(CoffeeOrder order,OrderState state){
        if(state.compareTo(order.getState())<=0){
            log.error("Wrong order state:{},{}",order,state);
            return false;
        }
        order.setState(state);
        coffeeOrderRepository.save(order);
        return true;
    }
}