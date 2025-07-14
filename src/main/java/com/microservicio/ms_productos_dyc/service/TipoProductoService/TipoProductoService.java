package com.microservicio.ms_productos_dyc.service.TipoProductoService;

import java.util.List;

import com.microservicio.ms_productos_dyc.model.dto.TipoProductoDTO;

public interface TipoProductoService {
    List<TipoProductoDTO> obtenerTodos();
    TipoProductoDTO crear(TipoProductoDTO dto);
    TipoProductoDTO actualizar(Long id, TipoProductoDTO dto);
    void cambiarEstado(Long id, Integer activo);
    TipoProductoDTO obtenerPorId(Long id);
    void eliminar(Long id);
}
