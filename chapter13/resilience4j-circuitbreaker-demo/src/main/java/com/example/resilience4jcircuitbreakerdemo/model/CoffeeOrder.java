package com.example.resilience4jcircuitbreakerdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/19
 * <description>：TODO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder {
    private Long id;
    private String customer;
    private List<Coffee> items;
    private OrderState state;
    private Date createTime;
    private Date updateTime;
}