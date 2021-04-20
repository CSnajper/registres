package com.in4mo.registers.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistryTransferDto extends RegistryRechargeDto {
    private String fromRegistry;
}
