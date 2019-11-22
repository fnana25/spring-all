package com.example.thymeleafviewdemo.controller.request;

import com.example.thymeleafviewdemo.model.Coffee;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/22
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