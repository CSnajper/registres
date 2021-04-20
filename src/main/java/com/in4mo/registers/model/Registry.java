package com.in4mo.registers.model;

import com.in4mo.registers.rest.error.InsufficientFundsException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Registry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registry_generator")
    @SequenceGenerator(name = "registry_generator", allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = "amount", columnDefinition = "bigint default 0")
    private Long amount;

    public void addAmount(Long amount) {
        this.amount += amount;
    }

    public void minusAmount(Long amount) {
        if(this.amount < amount) {
            throw new InsufficientFundsException(this.name, amount);
        }
        this.amount -= amount;
    }
}
