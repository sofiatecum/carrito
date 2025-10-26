package com.tecumsofia.carrito.view;

public class SolicitudPagoRequest {
    private String codUsuario;
    private String metodo;

    public SolicitudPagoRequest() {}

    public SolicitudPagoRequest(String codUsuario, String metodo) {
        this.codUsuario = codUsuario;
        this.metodo = metodo;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}
