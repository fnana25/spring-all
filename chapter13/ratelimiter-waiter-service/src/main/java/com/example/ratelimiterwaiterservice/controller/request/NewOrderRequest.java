package com.example.ratelimiterwaiterservice.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/20
 * <description>：TODO
 */
@Getter
@Setter
@ToString
public class NewOrderRequest {

    @NotEmpty
    private String customer;

    @NotEmpty
    private List<String> items;
}