package com.example.gitconfigwaiterservice.repository;

import com.example.gitconfigwaiterservice.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder,Long> {
}
