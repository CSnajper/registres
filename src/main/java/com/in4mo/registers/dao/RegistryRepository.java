package com.in4mo.registers.dao;

import com.in4mo.registers.model.Registry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistryRepository extends JpaRepository<Registry, Long> {

    Registry findOneByName(String name);
}
