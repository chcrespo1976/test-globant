package com.techtest.serviciocuentamovimiento.service;

import com.techtest.serviciocuentamovimiento.entity.Cuenta;
import com.techtest.serviciocuentamovimiento.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public Optional<Cuenta> get(long cuentaId) {
        return cuentaRepository.findById(cuentaId);
    }

    public List<Cuenta> getAll() {
        return cuentaRepository.findAll();
    }

    public Cuenta put(long cuentaId, Cuenta cuenta) {
        Cuenta cuentaActualiza = cuentaRepository.findById(cuentaId).get();
        if(cuentaActualiza != null){
            cuentaActualiza.setCuentaNumero(cuenta.getCuentaNumero());
            cuentaActualiza.setCuentaTipo(cuenta.getCuentaTipo());
            cuentaActualiza.setCuentaSaldoInicial(cuenta.getCuentaSaldoInicial());
            cuentaActualiza.setCuentaEstado(cuenta.isCuentaEstado());
        }
        return cuentaRepository.save(cuentaActualiza);
    }

    public Cuenta post(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Optional<Cuenta> delete(long cuentaId) {
        Optional<Cuenta> cuentaEliminar = cuentaRepository.findById(cuentaId);
        if(cuentaEliminar.get() != null){
            cuentaRepository.delete(cuentaEliminar.get());
        }
        return cuentaEliminar;
    }

}
