package com.tecumsofia.carrito.service;

import com.tecumsofia.carrito.entity.Pago;
import com.tecumsofia.carrito.pattern.strategy.pago.*;
import com.tecumsofia.carrito.repository.PagoRepository;
import com.tecumsofia.carrito.view.PagoResumenView;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PagoService {
    private final CarritoService carritoService;
    private final PagoRepository pagoRepository;

    public PagoService(CarritoService carritoService, PagoRepository pagoRepository) {
        this.carritoService = carritoService;
        this.pagoRepository = pagoRepository;
    }

    public Pago realizarPago(String codUsuario, String metodo) {
        BigDecimal monto = carritoService.calcularTotal(codUsuario);

        MetodoPago estrategia = switch (metodo.toLowerCase()) {
            case "efectivo" -> new PagoEfectivo();
            case "tarjeta_credito" -> new PagoTarjetaCredito();
            case "paypal" -> new PagoPayPal();
            default -> throw new IllegalArgumentException("Método de pago no soportado");
        };

        ProcesadorPago procesador = new ProcesadorPago(estrategia);
        boolean aprobado = procesador.ejecutarPago(monto);

        Pago pago = new Pago(
                codUsuario,
                monto,
                metodo,
                aprobado ? "aprobado" : "rechazado",
                LocalDateTime.now()
        );
        return pagoRepository.save(pago);
    }

    public PagoResumenView realizarPagoConResumen(String codUsuario, String metodo) {
        Pago pago = realizarPago(codUsuario, metodo);

        return new PagoResumenView(
                pago.getId(),
                pago.getCodUsuario(),
                pago.getMonto(),
                pago.getMetodo(),
                pago.getEstado(),
                pago.getFecha()
        );
    }
}
