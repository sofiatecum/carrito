package com.tecumsofia.carrito.pattern.strategy.pago;

import java.math.BigDecimal;

public class ProcesadorPago {
    private MetodoPago metodo;

    public ProcesadorPago(MetodoPago metodo){
        this.metodo = metodo;
    }

    public boolean ejecutarPago(BigDecimal monto){
        return metodo.procesarPago(monto);
    }
}
