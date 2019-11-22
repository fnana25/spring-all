package com.example.thymeleafviewdemo.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.money.Money;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @uthor : fengna
 * @create 2019/11/22
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