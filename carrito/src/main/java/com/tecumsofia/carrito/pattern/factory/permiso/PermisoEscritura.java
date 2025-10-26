package com.tecumsofia.carrito.pattern.factory.permiso;

public class PermisoEscritura implements Permiso{
    @Override
    public void aplicar(String codUsuario) {
        System.out.println("Otorgando permiso de escritura a usuario: " + codUsuario);
    }
}
