package com.tecumsofia.carrito.repository;
import com.tecumsofia.carrito.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCodigo(String codigo);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
