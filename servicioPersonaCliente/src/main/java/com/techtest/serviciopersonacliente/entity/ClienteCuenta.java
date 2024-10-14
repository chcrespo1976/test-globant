package com.techtest.serviciopersonacliente.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente-cuenta", schema = "microservicio")
public class ClienteCuenta {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private long cuentaId;
    @Transient
    private String cuentaNumero;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Cliente.class)
    @JoinColumn(name = "personaId", nullable = true)
    private Cliente cliente;

}
