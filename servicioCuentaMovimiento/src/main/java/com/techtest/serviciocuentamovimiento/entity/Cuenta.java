package com.techtest.serviciocuentamovimiento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuenta", schema = "microservicio")
public class Cuenta {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cuentaId;
    private String cuentaNumero;
    private String cuentaTipo;
    private double cuentaSaldoInicial;
    private boolean cuentaEstado;
}
