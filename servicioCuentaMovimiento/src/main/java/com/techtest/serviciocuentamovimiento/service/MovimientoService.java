package com.techtest.serviciocuentamovimiento.service;

import com.techtest.serviciocuentamovimiento.entity.Cuenta;
import com.techtest.serviciocuentamovimiento.entity.Movimiento;
import com.techtest.serviciocuentamovimiento.repository.CuentaRepository;
import com.techtest.serviciocuentamovimiento.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;


    public Optional<Movimiento> get(long movimientoId) {
        return movimientoRepository.findById(movimientoId);
    }

    public List<Movimiento> getAll() {
        return movimientoRepository.findAll();
    }

    public Movimiento put(long movimientoId, Movimiento movimiento) {
        Movimiento movimientoActualiza = movimientoRepository.findById(movimientoId).get();
        if(movimientoActualiza != null){
            movimientoActualiza.setMovimientoFecha(movimiento.getMovimientoFecha());
            movimientoActualiza.setMovimientoTipo(movimiento.getMovimientoTipo());
            movimientoActualiza.setMovimientoValor(movimiento.getMovimientoValor());
            movimientoActualiza.setMovimientoSaldo(movimiento.getMovimientoSaldo());
        }
        return movimientoRepository.save(movimientoActualiza);
    }

    public Movimiento post(Movimiento movimiento) {
        if(registrarMovimiento(movimiento) != null) {
            return movimientoRepository.save(movimiento);
        }
        else {
            return null;
        }

    }

    public Optional<Movimiento> delete(long movimientoId) {
        Optional<Movimiento> movimientoEliminar = movimientoRepository.findById(movimientoId);
        if(movimientoEliminar.get() != null){
            movimientoRepository.delete(movimientoEliminar.get());
        }
        return movimientoEliminar;
    }

    private boolean validarSaldoDisponible(Movimiento movimiento) {
        Cuenta cuenta = movimiento.getCuenta();
        double saldo = cuenta.getCuentaSaldoInicial();
        double valor = movimiento.getMovimientoValor();
        if(valor >= 0) {
            return true;
        }
        else {
            if(saldo > 0 && saldo > Math.abs(movimiento.getMovimientoValor())) {
                return true;
            }
            else {
                return false;
            }
        }

    }

    private Movimiento registrarMovimiento(Movimiento movimiento) {
        if(validarSaldoDisponible(movimiento)) {
            double nuevoSaldo = movimiento.getMovimientoSaldo() + movimiento.getMovimientoValor();
            movimiento.setMovimientoSaldo(nuevoSaldo);
            movimiento.getCuenta().setCuentaSaldoInicial(nuevoSaldo);
            cuentaRepository.save(movimiento.getCuenta());
            return movimientoRepository.save(movimiento);
        }
        else {
            return null;
        }

    }

}
