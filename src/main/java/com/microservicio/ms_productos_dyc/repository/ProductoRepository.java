package com.microservicio.ms_productos_dyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.ms_productos_dyc.model.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivo(Integer activo);
}
