package com.example.redisdemo.repostories;

import com.example.redisdemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepostory extends JpaRepository<Coffee,Long> {
}
