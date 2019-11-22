package com.example.thymeleafviewdemo.repository;

import com.example.thymeleafviewdemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    Coffee findByName(String name);

    List<Coffee> findByNameInOrderById(List<String> list);
}
