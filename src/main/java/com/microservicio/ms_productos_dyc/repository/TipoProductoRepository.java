package com.microservicio.ms_productos_dyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.ms_productos_dyc.model.entity.TipoProducto;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long>{    
}
