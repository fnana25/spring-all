package com.example.consulwaiterservice.repository;

import com.example.consulwaiterservice.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
