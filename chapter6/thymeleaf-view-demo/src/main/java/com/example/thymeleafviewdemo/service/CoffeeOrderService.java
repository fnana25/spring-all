package com.example.thymeleafviewdemo.service;

import com.example.thymeleafviewdemo.model.Coffee;
import com.example.thymeleafviewdemo.model.CoffeeOrder;
import com.example.thymeleafviewdemo.model.OrderState;
import com.example.thymeleafviewdemo.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * @uthor : fengna
 * @create 2019/11/22
 * <description>：TODO
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    public CoffeeOrder findById(Long id){
        return orderRepository.getOne(id);
    }

    public CoffeeOrder createOrder(String customer, Coffee...coffees){
        CoffeeOrder order = CoffeeOrder.builder().customer(customer).items(Arrays.asList(coffees)).state(OrderState.INIT).build();
        CoffeeOrder saved = orderRepository.save(order);
        log.info("Order created: {}",saved);
        return saved;
    }

    public boolean updateOrder(CoffeeOrder order,OrderState state){
        if(state.compareTo(order.getState())<=0){
            log.error("Wrong order state:{},{}",order,state);
            return false;
        }
        order.setState(state);
        orderRepository.save(order);
        log.info("Order updated:{}",order);
        return true;
    }
}