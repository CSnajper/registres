package com.in4mo.registers.service;

import com.in4mo.registers.dao.RegistryRepository;
import com.in4mo.registers.model.Registry;
import com.in4mo.registers.rest.dto.RegistryDto;
import com.in4mo.registers.rest.dto.RegistryTransferDto;
import com.in4mo.registers.rest.dto.RegistryRechargeDto;
import com.in4mo.registers.rest.error.RegistryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistryService {

    @Autowired
    RegistryRepository registryRepository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void recharge(RegistryRechargeDto dto) {
        Registry registry = registryRepository.findOneByName(dto.getToRegistry())
                .orElseThrow(() -> new RegistryNotFoundException(dto.getToRegistry()));
        registry.addAmount(dto.getAmount());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void transfer(RegistryTransferDto dto) {
        Registry fromRegistry = registryRepository.findOneByName(dto.getFromRegistry())
                .orElseThrow(() -> new RegistryNotFoundException(dto.getFromRegistry()));
        Registry toRegistry = registryRepository.findOneByName(dto.getToRegistry())
                .orElseThrow(() -> new RegistryNotFoundException(dto.getToRegistry()));

        fromRegistry.minusAmount(dto.getAmount());
        toRegistry.addAmount(dto.getAmount());
    }

    public List<RegistryDto> list() {
        return registryRepository.findAll().stream().map(registry -> new RegistryDto(registry.getName(), registry.getAmount())).collect(Collectors.toList());
    }
}
