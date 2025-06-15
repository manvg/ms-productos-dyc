package com.microservicio.ms_productos_dyc.service;

import com.microservicio.ms_productos_dyc.model.dto.ProductoDTO;
import com.microservicio.ms_productos_dyc.model.entity.Producto;
import com.microservicio.ms_productos_dyc.repository.ProductoRepository;
import com.microservicio.ms_productos_dyc.utilities.ProductoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    //---------MÉTODOS GET---------//
    @Override
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll().stream().map(ProductoMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> obtenerActivos() {
        return productoRepository.findByActivo(1).stream().map(ProductoMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProductoDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ProductoMapper.toDTO(producto);
    }

    //---------MÉTODOS POST---------//
    @Override
    public ProductoDTO crear(ProductoDTO dto) {
        Producto producto = ProductoMapper.toEntity(dto);
        return ProductoMapper.toDTO(productoRepository.save(producto));
    }


    //---------MÉTODOS PUT---------//
    @Override
    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Producto existente = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setMaterial(dto.getMaterial());
        existente.setMedidas(dto.getMedidas());
        existente.setPrecio(dto.getPrecio());
        existente.setUrlImagen(dto.getUrlImagen());
        existente.setActivo(dto.getActivo());

        if (dto.getIdTipoProducto() != null) {
            var tipo = new com.microservicio.ms_productos_dyc.model.entity.TipoProducto();
            tipo.setIdTipoProducto(dto.getIdTipoProducto());
            existente.setTipoProducto(tipo);
        }

        return ProductoMapper.toDTO(productoRepository.save(existente));
    }

    @Override
    public void cambiarEstado(Long id, Integer activo) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        producto.setActivo(activo);
        productoRepository.save(producto);
    }

    //---------MÉTODOS DELETE---------//
    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}