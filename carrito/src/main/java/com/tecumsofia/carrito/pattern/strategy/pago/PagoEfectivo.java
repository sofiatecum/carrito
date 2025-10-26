package com.tecumsofia.carrito.pattern.strategy.pago;

import java.math.BigDecimal;

public class PagoEfectivo implements MetodoPago{
    @Override
    public boolean procesarPago(BigDecimal monto) {
        System.out.println("Procesando pago en efectivo: Q" + monto);
        return true;
    }
}
