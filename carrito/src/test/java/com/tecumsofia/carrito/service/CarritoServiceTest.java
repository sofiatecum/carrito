package com.tecumsofia.carrito.service;
import com.tecumsofia.carrito.entity.Carrito;
import com.tecumsofia.carrito.entity.CarritoItem;
import com.tecumsofia.carrito.entity.Inventario;
import com.tecumsofia.carrito.entity.Producto;
import com.tecumsofia.carrito.repository.CarritoItemRepository;
import com.tecumsofia.carrito.repository.CarritoRepository;
import com.tecumsofia.carrito.view.CarritoResumenView;
import jakarta.inject.Inject;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

 @RunWith(MockitoJUnitRunner.class)
public class CarritoServiceTest {

     @InjectMocks
     private CarritoService carritoService;
     @Mock
     private CarritoRepository carritoRepository;
     @Mock
     private CarritoItemRepository carritoItemRepository;
     @Mock
     private InventarioService inventarioService;

     @Test
     public void testObtenerCarritoActivo(){
         String codUsuario = "123";
         Carrito carritoMock = new Carrito();
         carritoMock.setCodUsuario(codUsuario);
         carritoMock.setEstado("activo");

         Mockito.when(carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo"))
                 .thenReturn(Optional.of(carritoMock));
         Carrito result = carritoService.obtenerCarritoActivo(codUsuario);

         Assert.assertEquals(codUsuario, result.getCodUsuario());
         Assert.assertEquals("activo", result.getEstado());
     }

     @Test
     public void testAgregarItem(){
         Long idInventario = 1L;
         int cantidad = 2;
         String codUsuario = "123";

         Producto producto = new Producto();
         producto.setPrecio(50.0);

         Inventario inventario = new Inventario();
         inventario.setProducto(producto);

         Carrito carrito = new Carrito();
         carrito.setCodUsuario(codUsuario);

         Mockito.when(inventarioService.obtenerPorProducto(idInventario))
                 .thenReturn(Optional.of(inventario));

         Mockito.when(inventarioService.reservarStock(inventario, cantidad)).thenReturn(inventario);

         Mockito.when(carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo"))
                 .thenReturn(Optional.of(carrito));

         Mockito.when(carritoItemRepository.save(Mockito.any(CarritoItem.class)))
                 .thenAnswer(invocation-> invocation.getArgument(0));

         CarritoItem result = carritoService.agregarItem(idInventario, cantidad, codUsuario);

         Assert.assertEquals(cantidad, result.getCantidad().intValue());
         Assert.assertEquals(carrito, result.getCarrito());
         Assert.assertEquals(inventario, result.getInventario());
         Assert.assertEquals(BigDecimal.valueOf(100.0), result.getSubTotal());
     }

     @Test
     public void testEliminarItem() {
         Long idItem = 5L;
         int cantidad = 2;
         Inventario inventario = new Inventario();
         CarritoItem item = new CarritoItem();
         item.setId(idItem);
         item.setCantidad(cantidad);
         item.setInventario(inventario);

         Mockito.when(carritoItemRepository.findById(idItem))
                 .thenReturn(Optional.of(item));

         Mockito.when(inventarioService.liberarStock(inventario, cantidad))
                 .thenReturn(inventario);
         Mockito.doNothing().when(carritoItemRepository).delete(item);

         carritoService.eliminarItem(idItem);

         Mockito.verify(inventarioService).liberarStock(inventario, cantidad);
         Mockito.verify(carritoItemRepository).delete(item);
     }

     @Test
     public void testVaciarCarrito(){
         String codUsuario = "123";
         Producto producto = new Producto();
         producto.setPrecio(100.0);
         Inventario inventario = new Inventario();
         inventario.setProducto(producto);
         CarritoItem item1 = new CarritoItem();
         item1.setCantidad(2);
         item1.setInventario(inventario);
         CarritoItem item2 = new CarritoItem();
         item2.setCantidad(1);
         item2.setInventario(inventario);
         List<CarritoItem> items = Arrays.asList(item1, item2);

         Carrito carrito = new Carrito();
         carrito.setCodUsuario(codUsuario);
         carrito.setEstado("activo");
         carrito.setItems(items);

         Mockito.when(carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo"))
                 .thenReturn(Optional.of(carrito));
         Mockito.when(inventarioService.liberarStock(inventario, 2)).thenReturn(inventario);
         Mockito.when(inventarioService.liberarStock(inventario, 1)).thenReturn(inventario);
         Mockito.doNothing().when(carritoItemRepository).delete(item1);
         Mockito.doNothing().when(carritoItemRepository).delete(item2);
         carritoService.vaciarCarrito(codUsuario);
         Mockito.verify(inventarioService).liberarStock(inventario, 2);
         Mockito.verify(inventarioService).liberarStock(inventario, 1);
         Mockito.verify(carritoItemRepository).delete(item1);
         Mockito.verify(carritoItemRepository).delete(item2);
     }

     @Test(expected = RuntimeException.class)
     public void testVaciarCarritoInexistente(){
         String codUsuario = "999";
         Mockito.when(carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo"))
                 .thenReturn(Optional.empty());
         carritoService.vaciarCarrito(codUsuario);
         Mockito.verifyNoInteractions(inventarioService);
         Mockito.verifyNoInteractions(carritoItemRepository);
     }

     @Test
     public void testActualizarCantidad() {
         Long idItem = 10L;
         int nuevaCantidad = 3;

         Producto producto = new Producto();
         producto.setPrecio(100.0);
         Inventario inventario = new Inventario();
         inventario.setProducto(producto);
         inventario.setCantidadDisponible(5);
         CarritoItem item = new CarritoItem();
         item.setId(idItem);
         item.setCantidad(2);
         item.setInventario(inventario);
         item.setSubTotal(BigDecimal.valueOf(200.0));

         Mockito.when(carritoItemRepository.findById(idItem))
                 .thenReturn(Optional.of(item));
         Mockito.when(inventarioService.liberarStock(inventario, 2))
                 .thenReturn(inventario);
         Mockito.when(inventarioService.reservarStock(inventario, nuevaCantidad))
                 .thenReturn(inventario);

         Mockito.when(carritoItemRepository.save(Mockito.any(CarritoItem.class)))
                 .thenAnswer(invocation -> invocation.getArgument(0));

         CarritoItem actualizado = carritoService.actualizarCantidad(idItem, nuevaCantidad);

         Assertions.assertEquals(nuevaCantidad, actualizado.getCantidad());
         Assertions.assertEquals(BigDecimal.valueOf(300.0), actualizado.getSubTotal());
         Mockito.verify(inventarioService).liberarStock(inventario, 2);
         Mockito.verify(inventarioService).reservarStock(inventario, nuevaCantidad);
         Mockito.verify(carritoItemRepository).save(actualizado);
     }

     @Test(expected = IllegalArgumentException.class)
     public void testActualizarCantidadInvalida() {
         Long idItem = 10L;
         int nuevaCantidad = 0;

         CarritoItem item = new CarritoItem();
         item.setId(idItem);
         item.setCantidad(2);
         item.setInventario(new Inventario());

         Mockito.when(carritoItemRepository.findById(idItem))
                 .thenReturn(Optional.of(item));

         carritoService.actualizarCantidad(idItem, nuevaCantidad);
     }

     @Test(expected = IllegalArgumentException.class)
     public void testActualizarCantidadStockInsuficiente() {
         Long idItem = 10L;
         int nuevaCantidad = 10;

         Producto producto = new Producto();
         producto.setPrecio(100.0);

         Inventario inventario = new Inventario();
         inventario.setProducto(producto);
         inventario.setCantidadDisponible(5);
         CarritoItem item = new CarritoItem();
         item.setId(idItem);
         item.setCantidad(2);
         item.setInventario(inventario);

         Mockito.when(carritoItemRepository.findById(idItem))
                 .thenReturn(Optional.of(item));

         carritoService.actualizarCantidad(idItem, nuevaCantidad);
     }

     @Test
     public void testCalcularTotalCarrito() {
         String codUsuario = "123";

         CarritoItem item1 = new CarritoItem();
         item1.setSubTotal(BigDecimal.valueOf(100.0));

         CarritoItem item2 = new CarritoItem();
         item2.setSubTotal(BigDecimal.valueOf(250.0));

         Carrito carrito = new Carrito();
         carrito.setCodUsuario(codUsuario);
         carrito.setEstado("activo");
         carrito.setItems(List.of(item1, item2));

         Mockito.when(carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo"))
                 .thenReturn(Optional.of(carrito));

         BigDecimal total = carritoService.calcularTotal(codUsuario);

         Assertions.assertEquals(BigDecimal.valueOf(350.0), total);
     }

     @Test(expected = RuntimeException.class)
     public void testCalcularTotalCarritoNoEncontrado() {
         String codUsuario = "999";

         Mockito.when(carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo"))
                 .thenReturn(Optional.empty());

         carritoService.calcularTotal(codUsuario);
     }

     @Test
     public void testCalcularTotalCarritoVacio() {
         String codUsuario = "456";

         Carrito carrito = new Carrito();
         carrito.setCodUsuario(codUsuario);
         carrito.setEstado("activo");
         carrito.setItems(Collections.emptyList());

         Mockito.when(carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo"))
                 .thenReturn(Optional.of(carrito));

         BigDecimal total = carritoService.calcularTotal(codUsuario);

         Assertions.assertEquals(BigDecimal.ZERO, total);
     }

     @Test
     public void testObtenerResumenItems() {
         String codUsuario = "123";

         Producto productoA = new Producto();
         productoA.setNombre("Producto A");
         productoA.setPrecio(100.0);

         Producto productoB = new Producto();
         productoB.setNombre("Producto B");
         productoB.setPrecio(150.0);

         Inventario inventarioA = new Inventario();
         inventarioA.setProducto(productoA);

         Inventario inventarioB = new Inventario();
         inventarioB.setProducto(productoB);

         CarritoItem item1 = new CarritoItem();
         item1.setCantidad(2);
         item1.setSubTotal(BigDecimal.valueOf(200.0));
         item1.setInventario(inventarioA);

         CarritoItem item2 = new CarritoItem();
         item2.setCantidad(1);
         item2.setSubTotal(BigDecimal.valueOf(150.0));
         item2.setInventario(inventarioB);

         Carrito carrito = new Carrito();
         carrito.setCodUsuario(codUsuario);
         carrito.setEstado("activo");
         carrito.setItems(List.of(item1, item2));

         Mockito.when(carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo"))
                 .thenReturn(Optional.of(carrito));

         CarritoResumenView resumen = carritoService.obtenerResumen(codUsuario);

         Assertions.assertEquals(codUsuario, resumen.getCodUsuario());
         Assertions.assertEquals(3, resumen.getTotalItems());
         Assertions.assertEquals(BigDecimal.valueOf(350.0), resumen.getTotal());
         Assertions.assertEquals(2, resumen.getItems().size());
         Assertions.assertEquals("Producto A", resumen.getItems().get(0).getNombreProducto());
     }

     @Test
     public void testObtenerResumenCarritoVacio() {
         String codUsuario = "456";

         Carrito carrito = new Carrito();
         carrito.setCodUsuario(codUsuario);
         carrito.setEstado("activo");
         carrito.setItems(Collections.emptyList());

         Mockito.when(carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo"))
                 .thenReturn(Optional.of(carrito));

         CarritoResumenView resumen = carritoService.obtenerResumen(codUsuario);

         Assertions.assertEquals(codUsuario, resumen.getCodUsuario());
         Assertions.assertEquals(0, resumen.getTotalItems());
         Assertions.assertEquals(BigDecimal.ZERO, resumen.getTotal());
         Assertions.assertTrue(resumen.getItems().isEmpty());
     }

     @Test(expected = RuntimeException.class)
     public void testObtenerResumenNoEncontrado(){
         String codUsuario = "999";
         Mockito.when(carritoRepository.findByCodUsuarioAndEstado(codUsuario, "activo"))
                 .thenReturn(Optional.empty());
         carritoService.obtenerResumen(codUsuario);
     }
}