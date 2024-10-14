package com.techtest.serviciopersonacliente.cliente.repository;

import com.techtest.serviciopersonacliente.entity.Cliente;
import com.techtest.serviciopersonacliente.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void crearCliente() {
        Cliente cliente = new Cliente();
        assertThat(cliente.getPersonaId()).isEqualTo(0);
        // create
        cliente.setPersonaNombre("Unit Test 1");
        cliente = clienteRepository.save(cliente);
        assertThat(cliente.getPersonaId()).isGreaterThan(0);
        // get
        long id = cliente.getPersonaId();
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        assertThat(optionalCliente).isNotEmpty();
    }
}
