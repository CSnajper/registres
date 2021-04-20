package com.in4mo.registers.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
public class Registry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = "amount", columnDefinition = "bigint default 0")
    private Long amount;

    public void addAmount(Long amount) {
        this.amount += amount;
    }
}
