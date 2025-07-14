package com.microservicio.ms_productos_dyc.service.servicio;

import java.util.List;

import com.microservicio.ms_productos_dyc.model.dto.ServicioDTO;

public interface ServicioService {
    List<ServicioDTO> obtenerTodos();
    List<ServicioDTO> obtenerActivos();
    ServicioDTO obtenerPorId(Long id);
    ServicioDTO crear(ServicioDTO dto);
    ServicioDTO actualizar(Long id, ServicioDTO dto);
    void cambiarEstado(Long id, Integer activo);
    void eliminar(Long id);
}
