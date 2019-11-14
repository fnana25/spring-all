package com.example.redisdemo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/13
 * <description>：TODO
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "T_ORDER")
public class CoffeeOrder extends BaseEntity {

    private String customer;

    @ManyToMany
    @OrderBy("id")
    @JoinTable(name = "T_ORDER_COFFEE")
    private List<Coffee> items;

    @Column(nullable = false)
    @Enumerated
    private OrderState state;
}