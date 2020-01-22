package com.example.gitconfigwaiterservice.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @uthor : fengna
 * @create 2020/1/22
 * <description>：TODO
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("order")
public class OrderProperty {

    private Integer discount = 100;

    private String waiterPrefix = "springbucks-";
}