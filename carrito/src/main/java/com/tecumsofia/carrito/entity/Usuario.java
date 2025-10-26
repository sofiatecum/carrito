package com.tecumsofia.carrito.entity;

import com.tecumsofia.carrito.pattern.factory.permiso.Permiso;

public class Usuario {
    private String codUsuario;
    private String nombre;
    private Permiso permiso;

    public Usuario(String codUsuario, String nombre) {
        this.codUsuario = codUsuario;
        this.nombre = nombre;
    }

    public void asignarPermiso(Permiso permiso){
        if (permiso == null) {
            throw new IllegalArgumentException("El permiso no puede ser nulo");
        }
        this.permiso = permiso;
        permiso.aplicar(codUsuario);
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public Permiso getPermiso() {
        return permiso;
    }
}
