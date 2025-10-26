package com.tecumsofia.carrito.repository;

import com.tecumsofia.carrito.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByCodUsuario(String codUsuario);
}
