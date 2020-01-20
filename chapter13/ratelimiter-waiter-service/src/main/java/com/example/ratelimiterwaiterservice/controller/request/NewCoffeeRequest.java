package com.example.ratelimiterwaiterservice.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.money.Money;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @uthor : fengna
 * @create 2020/1/20
 * <description>：TODO
 */
@Getter
@Setter
@ToString
public class NewCoffeeRequest {
    @NotEmpty
    private String name;

    @NotNull
    private Money price;
}