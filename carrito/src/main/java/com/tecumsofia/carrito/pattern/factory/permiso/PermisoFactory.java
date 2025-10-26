package com.tecumsofia.carrito.pattern.factory.permiso;

public class PermisoFactory {
    public static Permiso crearPermiso(String tipo){
        switch (tipo.toLowerCase()){
            case "lectura":
                return new PermisoLectura();
            case "escritura":
                return new PermisoEscritura();
            case "lectura_escritura":
                return new PermisoLecturaEscritura();
            default:
                throw new IllegalArgumentException("Tipo de permiso no valido: " + tipo);
        }
    }
}
