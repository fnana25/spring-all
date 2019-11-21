package com.example.jsonviewdemo.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/21
 * <description>：TODO
 */
@Getter
@Setter
@ToString
public class NewOrderReq {

    @NotEmpty
    private String customer;

    @NotEmpty
    private List<String> items;
}