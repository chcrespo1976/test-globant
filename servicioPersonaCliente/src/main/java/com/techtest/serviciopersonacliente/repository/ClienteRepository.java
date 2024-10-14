package com.techtest.serviciopersonacliente.repository;

import com.techtest.serviciopersonacliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
