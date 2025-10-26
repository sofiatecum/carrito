package com.tecumsofia.carrito.pattern.strategy.pago;

import java.math.BigDecimal;

public class PagoPayPal implements MetodoPago{
    @Override
    public boolean procesarPago(BigDecimal monto) {
        System.out.println("Procesando pago con PayPal: Q" + monto);
        return true;
    }
}
