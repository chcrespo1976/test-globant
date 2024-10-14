package com.techtest.serviciocuentamovimiento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movimiento", schema = "microservicio")
public class Movimiento {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long movimientoId;

    private LocalDate movimientoFecha;

    private String movimientoTipo;

    private double movimientoValor;

    private double movimientoSaldo;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;
}
