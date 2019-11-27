package com.example.simpleresttemplatedemo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @uthor : fengna
 * @create 2019/11/27
 * <description>：TODO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {

    private Long id;
    private String name;
    private BigDecimal price;
    private Date createTime;
    private Date updateTime;
}