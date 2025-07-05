package com.microservicio.ms_productos_dyc.service;

import java.util.List;

import com.microservicio.ms_productos_dyc.model.dto.ProductoDTO;
import com.microservicio.ms_productos_dyc.model.dto.ProductoDetalleDTO;
import com.microservicio.ms_productos_dyc.model.dto.ProductoSumarioDTO;

public interface ProductoService {
    List<ProductoDTO> obtenerTodos();
    List<ProductoDTO> obtenerActivos();
    ProductoDTO obtenerPorId(Long id);
    ProductoDTO crear(ProductoDTO dto);
    ProductoDTO actualizar(Long id, ProductoDTO dto);
    void cambiarEstado(Long id, Integer activo);
    void eliminar(Long id);

    //lista de prods pal expositor
    List<ProductoSumarioDTO> listarSumario();
    //Detalle de un producto para expositor
    ProductoDetalleDTO obtenerDetalle(Long id);
}
