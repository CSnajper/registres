package com.in4mo.registers.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistryRechargeDto {
    private String toRegistry;
    private Long amount;
}
