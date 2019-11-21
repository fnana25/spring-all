package com.example.morecomplexcontrollerdemo.service;

import com.example.morecomplexcontrollerdemo.model.Coffee;
import com.example.morecomplexcontrollerdemo.model.CoffeeOrder;
import com.example.morecomplexcontrollerdemo.model.OrderState;
import com.example.morecomplexcontrollerdemo.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

/**
 * @uthor : fengna
 * @create 2019/11/20
 * <description>：TODO
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public Optional<CoffeeOrder> getById(Long id){
        return coffeeOrderRepository.findById(id);
    }

    public CoffeeOrder createOrder(String customer, Coffee...coffees){
        CoffeeOrder order = CoffeeOrder.builder().customer(customer).items(Arrays.asList(coffees)).state(OrderState.INIT).build();
        CoffeeOrder saved = coffeeOrderRepository.save(order);
        log.info("Order created:{}",saved);
        return saved;
    }

    public boolean updateOrder(CoffeeOrder order,OrderState state){
        if(state.compareTo(order.getState())<=0){
            log.error("Wrong order state:{},{}",order,state);
            return false;
        }
        order.setState(state);
        coffeeOrderRepository.save(order);
        log.info("Order updated:{}",order);
        return true;
    }
}