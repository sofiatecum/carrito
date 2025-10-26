package com.tecumsofia.carrito.pattern.strategy.pago;

import java.math.BigDecimal;

public interface MetodoPago {
    boolean procesarPago(BigDecimal monto);
}
