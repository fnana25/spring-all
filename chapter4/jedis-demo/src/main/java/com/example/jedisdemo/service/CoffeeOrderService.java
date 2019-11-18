package com.example.jedisdemo.service;

import com.example.jedisdemo.model.Coffee;
import com.example.jedisdemo.model.CoffeeOrder;
import com.example.jedisdemo.model.OrderState;
import com.example.jedisdemo.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * @uthor : fengna
 * @create 2019/11/18
 * <description>：TODO
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public CoffeeOrder createOrder(String customer, Coffee... coffees) {
        CoffeeOrder order = CoffeeOrder.builder().customer(customer).items(Arrays.asList(coffees)).state(OrderState.INIT).build();
        CoffeeOrder saved = coffeeOrderRepository.save(order);
        log.info("Coffee saved : {}", order);
        return saved;
    }

    public boolean updateOrderState(CoffeeOrder order, OrderState state) {

        if (state.compareTo(order.getState()) <= 0) {
            log.error("Wrong Order State:{},{}", order.getState(), state);
            return false;
        }
        order.setState(state);
        coffeeOrderRepository.save(order);
        log.info("Updated Order : {}", order);
        return true;
    }
}