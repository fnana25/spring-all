package com.example.redisdemo.service;

import com.example.redisdemo.model.Coffee;
import com.example.redisdemo.model.CoffeeOrder;
import com.example.redisdemo.model.OrderState;
import com.example.redisdemo.repostories.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @uthor : fengna
 * @create 2019/11/13
 * <description>：TODO
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public CoffeeOrder saveOrder(String customer, Coffee... coffees) {
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(Arrays.asList(coffees))
                .state(OrderState.INIT)
                .build();
        CoffeeOrder saved = coffeeOrderRepository.save(order);
        log.info("New Order:{}", saved);
        return saved;
    }

    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getState()) < 0) {
            log.warn("Wrong state order: {},{}", state, order.getState());
            return false;
        }
        order.setState(state);
        coffeeOrderRepository.save(order);
        log.info("Updated Order :", order);
        return true;
    }
}