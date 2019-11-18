package com.example.jedisdemo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/18
 * <description>：TODO
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "T_ORDER")
public class CoffeeOrder extends BaseEntity {

    private String customer;

    @ManyToMany
    @OrderBy("id")
    @JoinTable(name = "T_COFFEE_ORDER")
    private List<Coffee> items;

    @Enumerated
    @Column(nullable = false)
    private OrderState state;
}