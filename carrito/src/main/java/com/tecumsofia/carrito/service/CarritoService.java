package com.tecumsofia.carrito.service;
import com.tecumsofia.carrito.entity.*;
import com.tecumsofia.carrito.repository.CarritoItemRepository;
import com.tecumsofia.carrito.repository.CarritoRepository;
import com.tecumsofia.carrito.service.InventarioService;
import com.tecumsofia.carrito.view.CarritoResumenView;
import com.tecumsofia.carrito.view.ItemResumenView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private CarritoItemRepository carritoItemRepository;
    @Autowired
    private InventarioService inventarioService;

    public Carrito obtenerCarritoActivo(String codUsuario){
        return carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo")
                .orElseGet(() -> {
                    Carrito nuevo = new Carrito();
                    nuevo.setCodUsuario(codUsuario);
                    return carritoRepository.save(nuevo);
                });
    }

    public CarritoItem agregarItem(Long idInventario, int cantidad, String codUsuario){
        Inventario inventario = inventarioService.obtenerPorProducto(idInventario)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        inventarioService.reservarStock(inventario, cantidad);
        Carrito carrito = obtenerCarritoActivo(codUsuario);
        CarritoItem item = new CarritoItem();
        item.setCarrito(carrito);
        item.setInventario(inventario);
        item.setCantidad(cantidad);
        item.setSubTotal(BigDecimal.valueOf(inventario.getProducto().getPrecio() * cantidad));
        return carritoItemRepository.save(item);
    }

    public void eliminarItem(Long idItem){
        CarritoItem item = carritoItemRepository.findById(idItem)
                .orElseThrow(()-> new RuntimeException("Item no encontrado"));
        inventarioService.liberarStock(item.getInventario(), item.getCantidad());
        carritoItemRepository.delete(item);
    }

    public void vaciarCarrito(String codUsuario) {
        Carrito carrito = carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo")
                .orElseThrow(()-> new RuntimeException("Carrito no encontrado"));
        List<CarritoItem>  items = carrito.getItems();

        for (CarritoItem item : items){
            Inventario inventario = item.getInventario();
            int cantidad = item.getCantidad();

            inventarioService.liberarStock(inventario, cantidad);
            carritoItemRepository.delete(item);
        }
        carrito.setItems(new ArrayList<>());
    }

    public CarritoItem actualizarCantidad(Long idItem, int nuevaCantidad){
        CarritoItem item = carritoItemRepository.findById(idItem)
                .orElseThrow(()-> new RuntimeException("Item no encontrado"));
        if (nuevaCantidad <= 0){
            throw  new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        Inventario inventario = item.getInventario();
        Producto producto = inventario.getProducto();
        int disponible = inventario.getCantidadDisponible() + item.getCantidad();
        if (nuevaCantidad > disponible){
            throw new IllegalArgumentException("No hay suficiente stock disponible");
        }
        inventarioService.liberarStock(inventario, item.getCantidad());
        inventarioService.reservarStock(inventario, nuevaCantidad);
        item.setCantidad(nuevaCantidad);
        item.setSubTotal(BigDecimal.valueOf(producto.getPrecio() * nuevaCantidad));
        return  carritoItemRepository.save(item);
    }

    public BigDecimal calcularTotal(String codUsuario){
        Carrito carrito = carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo")
                .orElseThrow(()-> new RuntimeException("Carrito no encontrado"));
        return carrito.getItems().stream().map(CarritoItem::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CarritoResumenView obtenerResumen(String codUsuario){
        Carrito carrito = carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo")
                .orElseThrow(()-> new RuntimeException("Carrito no encontrado"));
        List<ItemResumenView> items =  carrito.getItems().stream().map(carritoItem -> new ItemResumenView(
                carritoItem.getInventario().getProducto().getNombre(),
                carritoItem.getCantidad(),
                carritoItem.getSubTotal()
        )).collect(Collectors.toList());
        int totalItems = items.stream().mapToInt(ItemResumenView::getCantidad).sum();
        BigDecimal total = items.stream().map(ItemResumenView::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CarritoResumenView(codUsuario, totalItems, total, items);
    }
}
