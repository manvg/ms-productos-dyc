package com.microservicio.ms_productos_dyc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microservicio.ms_productos_dyc.model.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivo(Integer activo);
    
    @Query("SELECT p FROM Producto p JOIN FETCH p.tipoProducto WHERE p.id = :id")
    Optional<Producto> findByIdWithTipoProducto(@Param("id") Long id);
}
