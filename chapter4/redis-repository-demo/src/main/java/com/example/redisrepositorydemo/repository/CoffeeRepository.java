package com.example.redisrepositorydemo.repository;

import com.example.redisrepositorydemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @uthor : fengna
 * @create 2019/11/15
 */
public interface CoffeeRepository extends JpaRepository<Coffee,Long> {
}