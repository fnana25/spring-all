package com.example.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/12/23
 * <description>：TODO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder implements Serializable {

    private Long id;
    private String customer;
    private List<String> items;
    private OrderState state;
    private Date createTime;
    private Date updateTime;
}