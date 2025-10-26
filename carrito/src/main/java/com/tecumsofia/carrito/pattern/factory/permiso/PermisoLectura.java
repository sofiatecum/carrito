package com.tecumsofia.carrito.pattern.factory.permiso;

public class PermisoLectura implements Permiso {
    @Override
    public void aplicar(String codUsuario) {
        System.out.println("Otorgando permiso de lectura a usuario: " + codUsuario);
    }
}
