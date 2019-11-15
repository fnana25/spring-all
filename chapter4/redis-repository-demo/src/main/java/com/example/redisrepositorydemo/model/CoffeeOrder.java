package com.example.redisrepositorydemo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/15
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@ToString(callSuper = true)
public class CoffeeOrder extends BaseEntity {

    private String customer;

    @ManyToMany
    @OrderBy("id")
    @JoinTable(name = "T_ORDER_COFFEE")
    private List<Coffee> items;

    @Enumerated
    @Column(nullable = false)
    private OrderState state;
}