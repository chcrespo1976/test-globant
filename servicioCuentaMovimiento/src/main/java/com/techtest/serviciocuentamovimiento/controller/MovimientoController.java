package com.techtest.serviciocuentamovimiento.controller;

import com.techtest.serviciocuentamovimiento.entity.Movimiento;
import com.techtest.serviciocuentamovimiento.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(name = "id") long movimientoId) {
        Optional<Movimiento> movimiento = movimientoService.get(movimientoId);
        if(movimiento.isPresent()) {
            return ResponseEntity.ok(movimiento);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Movimiento>> getAll() {
        List<Movimiento> movimientoLista = movimientoService.getAll();
        if(movimientoLista.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(movimientoLista);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long movimientoId, @RequestBody Movimiento movimiento) {
        Movimiento movimientoActualizado = movimientoService.put(movimientoId, movimiento);
        return ResponseEntity.ok(movimientoActualizado);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Movimiento movimiento) {
        Movimiento movimientoNuevo = movimientoService.post(movimiento);
        if(movimientoNuevo != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(movimientoNuevo);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\n\t\"message\" : \"Fondos Insuficientes\"\n}");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long movimientoId) {
        Optional<Movimiento> movimientoEliminado = movimientoService.delete(movimientoId);
        return ResponseEntity.ok().build();
    }

}
