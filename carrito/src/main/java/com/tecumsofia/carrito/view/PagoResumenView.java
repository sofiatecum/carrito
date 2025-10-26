package com.tecumsofia.carrito.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoResumenView {
    private Long idPago;
    private String codUsuario;
    private BigDecimal monto;
    private String metodo;
    private String estado;
    private LocalDateTime fecha;

    public PagoResumenView(Long idPago, String codUsuario, BigDecimal monto, String metodo, String estado, LocalDateTime fecha) {
        this.idPago = idPago;
        this.codUsuario = codUsuario;
        this.monto = monto;
        this.metodo = metodo;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
