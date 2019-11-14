package com.example.simplejdbcdemo;

import lombok.Builder;
import lombok.Data;

/**
 * @uthor : fengna
 * @create 2019/11/1
 * <description>：TODO
 */
@Data
@Builder
public class Foo {
    private Long id;
    private String bar;
}