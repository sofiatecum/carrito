package com.tecumsofia.carrito.view;

import java.math.BigDecimal;

public class ItemResumenView {
    private String nombreProducto;
    private int cantidad;
    private BigDecimal subTotal;

    public ItemResumenView(String nombreProducto, int cantidad, BigDecimal subTotal) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
}
