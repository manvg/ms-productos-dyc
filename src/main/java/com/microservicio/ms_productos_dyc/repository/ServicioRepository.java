package com.microservicio.ms_productos_dyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.ms_productos_dyc.model.entity.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Long>{
    List<Servicio> findByActivo(Integer activo);
}
