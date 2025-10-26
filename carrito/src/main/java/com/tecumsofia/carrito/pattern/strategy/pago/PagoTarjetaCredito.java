package com.tecumsofia.carrito.pattern.strategy.pago;

import java.math.BigDecimal;

public class PagoTarjetaCredito implements MetodoPago{
    @Override
    public boolean procesarPago(BigDecimal monto) {
        System.out.println("Procesando pago con tarjeta de crédito: Q" + monto);
        return true;
    }
}
