package com.example.gitconfigwaiterservice.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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
@Table(name = "T_COFFEE")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Coffee extends BaseEntity implements Serializable {

    private String name;

    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;
}