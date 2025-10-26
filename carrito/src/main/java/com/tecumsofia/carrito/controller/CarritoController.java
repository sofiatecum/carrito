package com.tecumsofia.carrito.controller;
import com.tecumsofia.carrito.entity.Carrito;
import com.tecumsofia.carrito.entity.CarritoItem;
import com.tecumsofia.carrito.service.CarritoService;
import com.tecumsofia.carrito.view.CarritoResumenView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{codUsuario}")
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable String codUsuario){
        try {
            Carrito carrito = carritoService.obtenerCarritoActivo(codUsuario);
            return ResponseEntity.ok(carrito);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado");
        }
    }

    @PostMapping("/agregar")
    public CarritoItem agregarItem(@RequestParam Long idInventario, @RequestParam int cantidad, @RequestParam String codUsuario){
        return carritoService.agregarItem(idInventario, cantidad, codUsuario);
    }

    @DeleteMapping("/eliminar/{idItem}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long idItem){
        try {
            carritoService.eliminarItem(idItem);
            return  ResponseEntity.ok().build();
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item no encontrado");
        }
    }

    @DeleteMapping("/vaciar/{codUsuario}")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable String codUsuario){
        try {
            carritoService.vaciarCarrito(codUsuario);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado");
        }
    }

    @PutMapping("/actualizar-cantidad/{idItem}")
    public ResponseEntity<CarritoItem> actualizarCantidad(@PathVariable Long idItem, @RequestParam int nuevaCantidad){
        try {
            CarritoItem actualizado = carritoService.actualizarCantidad(idItem, nuevaCantidad);
            return  ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/total/{codUsuario}")
    public ResponseEntity<BigDecimal> calcularTotal(@PathVariable String codUsuario){
        try {
            BigDecimal total = carritoService.calcularTotal(codUsuario);
            return ResponseEntity.ok(total);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/resumen/{codUsuario}")
    public ResponseEntity<CarritoResumenView> obtenerResumen(@PathVariable String codUsuario){
        try {
            CarritoResumenView resumen = carritoService.obtenerResumen(codUsuario);
            return ResponseEntity.ok(resumen);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
