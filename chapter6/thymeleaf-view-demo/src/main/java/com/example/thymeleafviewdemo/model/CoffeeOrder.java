package com.example.thymeleafviewdemo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/22
 * <description>：TODO
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "T_ORDER")
public class CoffeeOrder extends BaseEntity implements Serializable {

    private String customer;

    @ManyToMany
    @OrderBy("id")
    @JoinTable(name = "T_ORDER_COFFEE")
    private List<Coffee> items;

    @Enumerated
    @Column(nullable = false)
    private OrderState state;
}