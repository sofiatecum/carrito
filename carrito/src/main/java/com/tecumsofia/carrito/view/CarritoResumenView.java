package com.tecumsofia.carrito.view;

import java.math.BigDecimal;
import java.util.List;

public class CarritoResumenView {
    private String codUsuario;
    private int totalItems;
    private BigDecimal total;
    private List<ItemResumenView> items;

    public CarritoResumenView(String codUsuario, int totalItems, BigDecimal total, List<ItemResumenView> items) {
        this.codUsuario = codUsuario;
        this.totalItems = totalItems;
        this.total = total;
        this.items = items;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<ItemResumenView> getItems() {
        return items;
    }

    public void setItems(List<ItemResumenView> items) {
        this.items = items;
    }
}
