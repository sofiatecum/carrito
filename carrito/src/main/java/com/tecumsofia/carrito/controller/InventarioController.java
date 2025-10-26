package com.tecumsofia.carrito.controller;
import com.tecumsofia.carrito.entity.Inventario;
import com.tecumsofia.carrito.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventario")

public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/producto/{idProducto}")
    public Optional<Inventario> obtenerPorProducto(@PathVariable Long idProducto){
        return inventarioService.obtenerPorProducto(idProducto);
    }
    @PutMapping("/reservar/{idInventario}")
    public Inventario reservar(@PathVariable Long idInventario, @RequestParam int cantidad){
        Inventario inventario = inventarioService
                .obtenerPorProducto(idInventario)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        return inventarioService.reservarStock(inventario, cantidad);
    }

}
