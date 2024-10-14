package com.techtest.serviciocuentamovimiento.repository;

import com.techtest.serviciocuentamovimiento.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
