package com.example.gitconfigwaiterservice.service;

import com.example.gitconfigwaiterservice.model.Coffee;
import com.example.gitconfigwaiterservice.model.CoffeeOrder;
import com.example.gitconfigwaiterservice.model.OrderState;
import com.example.gitconfigwaiterservice.repository.CoffeeOrderRepository;
import com.example.gitconfigwaiterservice.repository.CoffeeRepository;
import com.example.gitconfigwaiterservice.support.OrderProperty;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @uthor : fengna
 * @create 2020/1/22
 * <description>：TODO
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService implements MeterBinder {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    @Autowired
    private OrderProperty orderProperty;

    private String waiterId = UUID.randomUUID().toString();

    private Counter orderCounter;

    public CoffeeOrder createOrder(String customer, Coffee...coffees){
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(Arrays.asList(coffees))
                .state(OrderState.INIT)
                .waiter(orderProperty.getWaiterPrefix() + waiterId)
                .discount(orderProperty.getDiscount())
                .total(calcTotal(coffees))
                .build();
        CoffeeOrder saved = coffeeOrderRepository.save(order);
        orderCounter.increment();
        return saved;
    }

    public boolean updateOrder(CoffeeOrder order,OrderState state){
        if(state.compareTo(order.getState())<=0){
            log.warn("Wrong state:{},{}",order.getState(),state);
            return false;
        }
        order.setState(state);
        coffeeOrderRepository.save(order);
        log.info("Order state updated");
        return true;
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.orderCounter = meterRegistry.counter("order.count");
    }

    private Money calcTotal(Coffee... coffees){
        List<Money> list = Stream.of(coffees).map(Coffee::getPrice).collect(Collectors.toList());
        return Money.total(list).multipliedBy(orderProperty.getDiscount()).dividedBy(100, RoundingMode.HALF_UP);
    }
}