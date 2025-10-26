package com.tecumsofia.carrito.service;

import com.tecumsofia.carrito.entity.Inventario;
import com.tecumsofia.carrito.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public Optional<Inventario> obtenerPorProducto(Long id_producto){
        return inventarioRepository.findByProductoIdProducto(id_producto);
    }

    public Inventario reservarStock(Inventario inventario, int cantidad){
        inventario.reservar(cantidad);
        return inventarioRepository.save(inventario);
    }
    public Inventario liberarStock(Inventario inventario, int cantidad){
        inventario.liberar(cantidad);
        return inventarioRepository.save(inventario);
    }

    public boolean stockSuficiente(Inventario inventario, int cantidad){
        return inventario.getCantidadDisponible() >= cantidad;
    }
    public Inventario guardar(Inventario inventario){
        return inventarioRepository.save(inventario);
    }

}
