package com.example.ratelimiterwaiterservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @uthor : fengna
 * @create 2020/1/20
 * <description>：TODO
 */
@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
// 增加了jackson-datatype-hibernate5就不需要这个Ignore了
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;
}