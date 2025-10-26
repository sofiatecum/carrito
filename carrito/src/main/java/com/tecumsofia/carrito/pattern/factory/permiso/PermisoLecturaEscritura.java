package com.tecumsofia.carrito.pattern.factory.permiso;

public class PermisoLecturaEscritura implements Permiso{
    @Override
    public void aplicar(String codUsuario) {
        System.out.println("Otorgando permiso completo (lectura y escritura) a usuario: " + codUsuario);
    }
}
