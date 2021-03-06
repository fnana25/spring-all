package com.example.hystrixcustomerservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/17
 * <description>：TODO
 */
@Getter
@Setter
@Builder
public class NewOrderRequest {
    private String customer;
    private List<String> items;
}