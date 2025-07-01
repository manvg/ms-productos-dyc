package com.microservicio.ms_productos_dyc.service.MaterialSerice;

import java.util.List;

import com.microservicio.ms_productos_dyc.model.dto.MaterialDTO;

public interface MaterialService {
    List<MaterialDTO> obtenerTodos();
    MaterialDTO obtenerPorId(Long id);
    MaterialDTO crear(MaterialDTO dto);
    MaterialDTO actualizar(Long id, MaterialDTO dto);
    void cambiarEstado(Long id, Integer activo);
    void eliminar(Long id);
}
