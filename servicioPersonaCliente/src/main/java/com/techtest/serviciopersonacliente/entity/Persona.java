package com.techtest.serviciopersonacliente.entity;

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
@Table(name = "persona", schema = "microservicio")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long personaId;

    private String personaNombre;

    private String personaGenero;

    private int personaEdad;

    private String personaIdentificacion;

    private String personaDireccion;

    private String personaTelefono;

}
