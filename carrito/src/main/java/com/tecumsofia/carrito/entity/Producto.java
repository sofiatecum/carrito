package com.tecumsofia.carrito.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    private String nombre;
    private String codigo;
    private Double precio;

    //Constructores

    public Producto(){}
    public Producto(Long id_producto, String nombre, String codigo, Double precio) {
        this.idProducto = id_producto;
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
    }

    //getters y setters
    public Long getIdProducto() {
        return idProducto;
    }
    public void setIdproducto(Long id_producto) {
        this.idProducto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
