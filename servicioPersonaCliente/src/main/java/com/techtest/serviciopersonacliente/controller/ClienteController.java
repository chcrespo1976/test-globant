package com.techtest.serviciopersonacliente.controller;

import com.techtest.serviciopersonacliente.entity.Cliente;
import com.techtest.serviciopersonacliente.exception.BusinessRuleException;
import com.techtest.serviciopersonacliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(name = "id") long personaId) {
        Optional<Cliente> cliente = clienteService.get(personaId);
        if(cliente.isPresent()) {
            return ResponseEntity.ok(cliente);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {
        List<Cliente> clienteLista = clienteService.getAll();
        if(clienteLista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(clienteLista);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long personaId, @RequestBody Cliente cliente) {
        Cliente clienteActualizado = clienteService.put(personaId, cliente);
        return ResponseEntity.ok(clienteActualizado);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Cliente cliente) throws BusinessRuleException, UnknownHostException {
        Cliente clienteNuevo = clienteService.post(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteNuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long personaId) {
        Optional<Cliente> clienteEliminado = clienteService.delete(personaId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cuentas")
    public Cliente getCuentasCliente(@RequestParam(name = "id") long personaId) {
        Cliente cliente = clienteService.getCuentasCliente(personaId);
        return cliente;
    }

}
