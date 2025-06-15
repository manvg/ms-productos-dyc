package com.microservicio.ms_productos_dyc.service;

import java.util.List;

import com.microservicio.ms_productos_dyc.model.dto.ProductoDTO;

public interface ProductoService {
    List<ProductoDTO> obtenerTodos();
    List<ProductoDTO> obtenerActivos();
    ProductoDTO obtenerPorId(Long id);
    ProductoDTO crear(ProductoDTO dto);
    ProductoDTO actualizar(Long id, ProductoDTO dto);
    void desactivar(Long id);
    void eliminar(Long id);
}
