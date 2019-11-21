package com.example.jsonviewdemo.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.money.Money;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @uthor : fengna
 * @create 2019/11/21
 * <description>：TODO
 */
@Setter
@Getter
@ToString
public class NewCoffeeReq {

    @NotEmpty
    private String name;

    @NotNull
    private Money price;
}