package com.example.customerservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/12/23
 * <description>：TODO
 */
@Builder
@Getter
@Setter
public class NewOrderRequest implements Serializable {

    private String customer;

    private List<String> items;
}