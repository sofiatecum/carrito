package com.tecumsofia.carrito.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;
    private Integer cantidadDisponible;
    private Integer cantidadReservada;
    private LocalDateTime ultimaReserva;

    public void reservar(int cantidad){
        if (cantidadDisponible < cantidad){
            throw new IllegalArgumentException("Stock insuficiente para agregar al carrito");
        }
        this.cantidadDisponible -= cantidad;
        this.cantidadReservada += cantidad;
        this.ultimaReserva = LocalDateTime.now();
    }
    public void liberar(int cantidad){
        this.cantidadDisponible += cantidad;
        this.cantidadReservada -= cantidad;
    }

    //Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Integer getCantidadReservada() {
        return cantidadReservada;
    }

    public void setCantidadReservada(Integer cantidadReservada) {
        this.cantidadReservada = cantidadReservada;
    }

    public LocalDateTime getUltimaReserva() {
        return ultimaReserva;
    }

    public void setUltimaReserva(LocalDateTime ultimaReserva) {
        this.ultimaReserva = ultimaReserva;
    }
}
