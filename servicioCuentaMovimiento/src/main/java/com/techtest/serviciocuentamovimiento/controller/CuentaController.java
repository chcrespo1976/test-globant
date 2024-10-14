package com.techtest.serviciocuentamovimiento.controller;

import com.techtest.serviciocuentamovimiento.entity.Cuenta;
import com.techtest.serviciocuentamovimiento.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(name = "id") long cuentaId) {
        Optional<Cuenta> cuenta = cuentaService.get(cuentaId);
        if(cuenta.isPresent()) {
            return ResponseEntity.ok(cuenta);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> getAll() {
        List<Cuenta> cuentaLista = cuentaService.getAll();
        if(cuentaLista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(cuentaLista);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long cuentaId, @RequestBody Cuenta cuenta) {
        Cuenta cuentaActualizada = cuentaService.put(cuentaId, cuenta);
        return ResponseEntity.ok(cuentaActualizada);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Cuenta cuenta) {
        Cuenta cuentaNueva = cuentaService.post(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaNueva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long cuentaId) {
        Optional<Cuenta> cuentaEliminada = cuentaService.delete(cuentaId);
        return ResponseEntity.ok().build();
    }

}
