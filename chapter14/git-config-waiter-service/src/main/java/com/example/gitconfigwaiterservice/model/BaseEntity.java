package com.example.gitconfigwaiterservice.model;

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
 * @create 2020/1/22
 * <description>：TODO
 */
@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;
}