package com.example.gitconfigwaiterservice.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2020/1/22
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
    @OrderBy("id")
    @JoinTable(name="T_COFFEE_ORDER")
    private List<Coffee> items;

    @Embedded
    @Column(nullable = false)
    private OrderState state;

    private String waiter;

    private Integer discount;
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money total;

}