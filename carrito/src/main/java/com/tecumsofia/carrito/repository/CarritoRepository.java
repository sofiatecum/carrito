package com.tecumsofia.carrito.repository;
import com.tecumsofia.carrito.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByCodUsuarioAndEstado(String codUsuario, String estado);
}
