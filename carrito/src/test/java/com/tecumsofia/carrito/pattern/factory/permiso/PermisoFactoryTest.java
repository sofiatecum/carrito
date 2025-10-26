package com.tecumsofia.carrito.pattern.factory.permiso;

import org.junit.Test;

import static org.junit.Assert.*;

public class PermisoFactoryTest {

    @Test
    public void testCrearPermisoLectura() {
        Permiso permiso = PermisoFactory.crearPermiso("lectura");
        assertTrue(permiso instanceof PermisoLectura);
    }

    @Test
    public void testCrearPermisoEscritura() {
        Permiso permiso = PermisoFactory.crearPermiso("escritura");
        assertTrue(permiso instanceof PermisoEscritura);
    }

    @Test
    public void testPermisoLecturaEscritura(){
        Permiso permiso = PermisoFactory.crearPermiso("lectura_escritura");
        assertTrue(permiso instanceof  PermisoLecturaEscritura);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCrearPermisoInvalido() {
        PermisoFactory.crearPermiso("invalido");
    }

}