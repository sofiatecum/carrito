package com.tecumsofia.carrito.controller;
import com.tecumsofia.carrito.entity.Carrito;
import com.tecumsofia.carrito.entity.CarritoItem;
import com.tecumsofia.carrito.entity.Inventario;
import com.tecumsofia.carrito.entity.Producto;
import com.tecumsofia.carrito.service.CarritoService;
import com.tecumsofia.carrito.view.CarritoResumenView;
import com.tecumsofia.carrito.view.ItemResumenView;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CarritoController.class)
public class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarritoService carritoService;

    // Test POST
    @Test
    public void testAgregarItem() throws Exception {
        CarritoItem item = new CarritoItem();
        item.setCantidad(2);
        item.setSubTotal(BigDecimal.valueOf(100.0));

        Inventario inventario = new Inventario();
        item.setInventario(inventario);

        Carrito carrito = new Carrito();
        item.setCarrito(carrito);

        Mockito.when(carritoService.agregarItem(3L, 2, "123"))
                .thenReturn(item);

        mockMvc.perform(post("/api/carrito/agregar")
                        .param("idInventario", "3")
                        .param("cantidad", "2")
                        .param("codUsuario", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.subTotal").value(100.0));
    }

    //Test para DELETE

    @Test
    public void testEliminarItem() throws Exception{
        Long idItem = 5L;
        Mockito.doNothing().when(carritoService).eliminarItem(idItem);
        mockMvc.perform(delete("/api/carrito/eliminar/{idItem}", idItem))
                .andExpect(status().isOk());
        Mockito.verify(carritoService).eliminarItem(idItem);
    }

    @Test
    public void testEliminarItemNoExistente() throws Exception{
        Long idItem = 999L;
        Mockito.doThrow(new RuntimeException("Item no encontrado"))
                .when(carritoService).eliminarItem(idItem);
        mockMvc.perform(delete("/api/carrito/eliminar/{idItem}", idItem))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testVaciarCarrito() throws Exception{
        String codUsuario = "123";
        Mockito.doNothing().when(carritoService).vaciarCarrito(codUsuario);
        mockMvc.perform(delete("/api/carrito/vaciar/{codUsuario}", codUsuario))
                .andExpect(status().isOk());
        Mockito.verify(carritoService).vaciarCarrito(codUsuario);
    }

    @Test
    public void testVaciarCarritoInexistente() throws Exception{
        String codUsuario = "999";
        Mockito.doThrow(new RuntimeException("Carrito no encontrado"))
                .when(carritoService).vaciarCarrito(codUsuario);
        mockMvc.perform(delete("/api/carrito/vaciar/{codUsuario}", codUsuario))
                .andExpect(status().isNotFound());
    }

    //Tests para obtener carrito (GET)

    @Test
    public void testObtenerCarritoActivo() throws Exception{
        String codUsuario = "123";
        Producto producto = new Producto();
        producto.setNombre("Laptop");
        producto.setPrecio(500.0);
        Inventario inventario = new Inventario();
        inventario.setProducto(producto);
        CarritoItem item = new CarritoItem();
        item.setCantidad(1);
        item.setInventario(inventario);
        item.setSubTotal(BigDecimal.valueOf(500.0));
        Carrito carrito = new Carrito();
        carrito.setCodUsuario(codUsuario);
        carrito.setEstado("activo");
        carrito.setItems(List.of(item));

        Mockito.when(carritoService.obtenerCarritoActivo(codUsuario)).thenReturn(carrito);
        mockMvc.perform(get("/api/carrito/{codUsuario}", codUsuario))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codUsuario").value("123"))
                .andExpect(jsonPath("$.estado").value("activo"))
                .andExpect(jsonPath("$.items[0].cantidad").value(1))
                .andExpect(jsonPath("$.items[0].subTotal").value(500.0));
    }

    @Test
    public void testObtenerCarritoActivoInexistente() throws Exception {
        String codUsuario = "999";

        Mockito.when(carritoService.obtenerCarritoActivo(codUsuario))
                .thenThrow(new RuntimeException("Carrito no encontrado"));

        mockMvc.perform(get("/api/carrito/{codUsuario}", codUsuario))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testActualizarCantidad() throws Exception {
        Long idItem = 10L;
        int nuevaCantidad = 3;

        Producto producto = new Producto();
        producto.setPrecio(100.0);

        Inventario inventario = new Inventario();
        inventario.setProducto(producto);

        CarritoItem item = new CarritoItem();
        item.setId(idItem);
        item.setCantidad(nuevaCantidad);
        item.setInventario(inventario);
        item.setSubTotal(BigDecimal.valueOf(300.0));

        Mockito.when(carritoService.actualizarCantidad(idItem, nuevaCantidad))
                .thenReturn(item);

        mockMvc.perform(put("/api/carrito/actualizar-cantidad/{idItem}", idItem)
                        .param("nuevaCantidad", String.valueOf(nuevaCantidad)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(nuevaCantidad))
                .andExpect(jsonPath("$.subTotal").value(300.0));
    }

    @Test
    public void testActualizarCantidadInvalida() throws Exception {
        Long idItem = 10L;
        int nuevaCantidad = 0;

        Mockito.when(carritoService.actualizarCantidad(idItem, nuevaCantidad))
                .thenThrow(new IllegalArgumentException("Cantidad invalida"));

        mockMvc.perform(put("/api/carrito/actualizar-cantidad/{idItem}", idItem)
                        .param("nuevaCantidad", String.valueOf(nuevaCantidad)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testActualizarCantidadItemNoEncontrado() throws Exception {
        Long idItem = 999L;
        int nuevaCantidad = 2;

        Mockito.when(carritoService.actualizarCantidad(idItem, nuevaCantidad))
                .thenThrow(new RuntimeException("Item no encontrado"));

        mockMvc.perform(put("/api/carrito/actualizar-cantidad/{idItem}", idItem)
                        .param("nuevaCantidad", String.valueOf(nuevaCantidad)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCalcularTotal() throws Exception {
        String codUsuario = "123";
        BigDecimal total = BigDecimal.valueOf(350.0);

        Mockito.when(carritoService.calcularTotal(codUsuario)).thenReturn(total);

        mockMvc.perform(get("/api/carrito/total/{codUsuario}", codUsuario))
                .andExpect(status().isOk())
                .andExpect(content().string("350.0"));
    }

    @Test
    public void testCalcularTotalCarritoNoEncontrado() throws Exception {
        String codUsuario = "999";

        Mockito.when(carritoService.calcularTotal(codUsuario))
                .thenThrow(new RuntimeException("Carrito no encontrado"));

        mockMvc.perform(get("/api/carrito/total/{codUsuario}", codUsuario))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testObtenerResumen() throws Exception {
        String codUsuario = "123";

        List<ItemResumenView> items = List.of(
                new ItemResumenView("Producto A", 2, BigDecimal.valueOf(200.0)),
                new ItemResumenView("Producto B", 1, BigDecimal.valueOf(150.0))
        );

        CarritoResumenView resumen = new CarritoResumenView(codUsuario, 3, BigDecimal.valueOf(350.0), items);

        Mockito.when(carritoService.obtenerResumen(codUsuario)).thenReturn(resumen);

        mockMvc.perform(get("/api/carrito/resumen/{codUsuario}", codUsuario))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codUsuario").value("123"))
                .andExpect(jsonPath("$.totalItems").value(3))
                .andExpect(jsonPath("$.total").value(350.0));
    }
}