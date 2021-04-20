package com.in4mo.registers.rest.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistryRechargeDto {
    @NotEmpty
    private String toRegistry;
    @NotNull
    private Long amount;
}
