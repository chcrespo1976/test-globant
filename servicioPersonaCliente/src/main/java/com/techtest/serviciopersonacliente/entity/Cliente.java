package com.techtest.serviciopersonacliente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente", schema = "microservicio")
@PrimaryKeyJoinColumn(referencedColumnName = "personaId")
public class Cliente extends Persona{

    private String clienteContraseña;
    private boolean clienteEstado;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteCuenta> cuentas;
}
