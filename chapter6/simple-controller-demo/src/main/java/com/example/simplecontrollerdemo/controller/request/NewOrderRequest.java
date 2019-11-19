package com.example.simplecontrollerdemo.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/19
 * <description>：TODO
 */
@Getter
@Setter
@ToString
public class NewOrderRequest {

    private String customer;

    private List<String> items;
}