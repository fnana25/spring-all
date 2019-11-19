package com.example.simplecontrollerdemo.service;

import com.example.simplecontrollerdemo.model.Coffee;
import com.example.simplecontrollerdemo.model.CoffeeOrder;
import com.example.simplecontrollerdemo.model.OrderState;
import com.example.simplecontrollerdemo.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public CoffeeOrder createOrder(String customer, Coffee... coffees) {
        CoffeeOrder order = CoffeeOrder.builder().customer(customer).items(Arrays.asList(coffees)).state(OrderState.INIT).build();
        CoffeeOrder saved = coffeeOrderRepository.save(order);
        log.info("Order created:{}", saved);
        return saved;
    }

    public boolean updateOrderState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            log.error("Wrong order state : {},{}", order, state);
            return false;
        }
        order.setState(state);
        coffeeOrderRepository.save(order);
        log.info("Order updated:{}", order);
        return true;
    }
}
