package com.in4mo.registers.dao;

import com.in4mo.registers.model.Registry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistryRepository extends JpaRepository<Registry, Long> {

    Optional<Registry> findOneByName(String name);
    void deleteByName(String name);
}
