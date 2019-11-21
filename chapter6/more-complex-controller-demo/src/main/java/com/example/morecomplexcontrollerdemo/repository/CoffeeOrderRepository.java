package com.example.morecomplexcontrollerdemo.repository;

import com.example.morecomplexcontrollerdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @uthor : fengna
 * @create 2019/11/20
 * <description>：TODO
 */
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder,Long> {
}