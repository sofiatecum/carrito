package com.tecumsofia.carrito.service;

import com.tecumsofia.carrito.entity.Pago;
import com.tecumsofia.carrito.repository.PagoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PagoServiceTest {

    @InjectMocks
    private PagoService pagoService;

    @Mock
    private CarritoService carritoService;

    @Mock
    private PagoRepository pagoRepository;

    @Test
    public void testRegistrarPago_exitoso() {
        BigDecimal total = new BigDecimal("350.0");
        when(carritoService.calcularTotal("123")).thenReturn(total);

        Pago pagoMock = new Pago();
        pagoMock.setCodUsuario("123");
        pagoMock.setMonto(total);
        pagoMock.setMetodo("efectivo");
        pagoMock.setEstado("aprobado");
        pagoMock.setFecha(LocalDateTime.now());
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoMock);

        Pago resultado = pagoService.realizarPago("123", "efectivo");

        assertEquals("123", resultado.getCodUsuario());
        assertEquals("efectivo", resultado.getMetodo());
        assertEquals("aprobado", resultado.getEstado());
    }

}