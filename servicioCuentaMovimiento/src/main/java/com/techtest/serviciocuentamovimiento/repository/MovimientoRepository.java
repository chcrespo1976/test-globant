package com.techtest.serviciocuentamovimiento.repository;

import com.techtest.serviciocuentamovimiento.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}
