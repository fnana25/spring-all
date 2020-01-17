package com.example.hystrixcustomerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/17
 * <description>：TODO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder {
    private Long id;
    private List<String> items;
    private OrderStatus status;
    private Date createTime;
    private Date updateTime;
}