package com.tecumsofia.carrito.entity;

import com.tecumsofia.carrito.pattern.factory.permiso.Permiso;
import com.tecumsofia.carrito.pattern.factory.permiso.PermisoFactory;
import com.tecumsofia.carrito.pattern.factory.permiso.PermisoLecturaEscritura;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsuarioTest {
    @Test
    public void testAplicarPermisoLecturaEscritura() {
        // Crear usuario
        Usuario usuario = new Usuario("123", "Usuario1");

        // Crear permiso desde la fábrica
        Permiso permiso = PermisoFactory.crearPermiso("lectura_escritura");

        // Asignar permiso al usuario
        usuario.asignarPermiso(permiso);

        // Verificar que el permiso fue asignado
        assertNotNull(usuario.getPermiso());
        assertTrue(usuario.getPermiso() instanceof PermisoLecturaEscritura);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAsignarPermisoNulo() {
        Usuario usuario = new Usuario("999", "Usuario de prueba");
        usuario.asignarPermiso(null); // Esto debe lanzar una excepción
    }
}