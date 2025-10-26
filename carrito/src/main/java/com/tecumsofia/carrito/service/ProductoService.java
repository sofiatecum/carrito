package com.tecumsofia.carrito.service;
import com.tecumsofia.carrito.entity.Producto;
import com.tecumsofia.carrito.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos(){
        return productoRepository.findAll();
    }
    public Optional<Producto> obtenerPorId(Long id){
        return productoRepository.findById(id);
    }
    public Producto guardar(Producto producto){
        return productoRepository.save(producto);
    }
    public void eliminar(Long id){
        productoRepository.deleteById(id);
    }
    public List<Producto> buscarPorCodigo(String codigo){
        return productoRepository.findByCodigo(codigo);
    }
}
