package com.example.zkwaiterservice.service;

import com.example.zkwaiterservice.model.Coffee;
import com.example.zkwaiterservice.model.CoffeeOrder;
import com.example.zkwaiterservice.model.OrderState;
import com.example.zkwaiterservice.repository.CoffeeOrderRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@Transactional
@Slf4j
public class CoffeeOrderService implements MeterBinder {
    protected final CoffeeOrderRepository orderRepository;

    private Counter orderCounter = null;

    public CoffeeOrderService(CoffeeOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CoffeeOrder get(Long id) {
        return orderRepository.getOne(id);
    }

    public CoffeeOrder createOrder(String customer, Coffee...coffee) {
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(new ArrayList<>(Arrays.asList(coffee)))
                .state(OrderState.INIT)
                .build();
        CoffeeOrder saved = orderRepository.save(order);
        log.info("New Order: {}", saved);
        orderCounter.increment();
        return saved;
    }

    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order: {}, {}", state, order.getState());
            return false;
        }
        order.setState(state);
        orderRepository.save(order);
        log.info("Updated Order: {}", order);
        return true;
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.orderCounter = meterRegistry.counter("order.count");
    }
}
