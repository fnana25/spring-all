package com.example.jpacomplexdemo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/11
 * <description>：TODO
 */
@Data
@Entity
@Builder
@Table(name = "T_ORDER")
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CoffeeOrder extends BaseEntity {

    private String customer;

    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
    @OrderBy("id")
    private List<Coffee> items;

    @Enumerated
    @Column(nullable = false)
    private OrderState state;
}