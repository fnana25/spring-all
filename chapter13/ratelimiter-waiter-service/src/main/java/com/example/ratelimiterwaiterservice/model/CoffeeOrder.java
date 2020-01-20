package com.example.ratelimiterwaiterservice.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/20
 * <description>：TODO
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_ORDER")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CoffeeOrder extends BaseEntity implements Serializable {

    private String customer;

    @ManyToMany
    @JoinTable(name="T_COFFEE_ORDER")
    @OrderBy("id")
    private List<Coffee> items;

    @Embedded
    @Column(nullable = false)
    private OrderState state;
}