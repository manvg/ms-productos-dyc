package com.microservicio.ms_productos_dyc.service;

import com.microservicio.ms_productos_dyc.model.dto.ProductoDTO;
import com.microservicio.ms_productos_dyc.model.entity.Material;
import com.microservicio.ms_productos_dyc.model.entity.Producto;
import com.microservicio.ms_productos_dyc.model.entity.TipoProducto;
import com.microservicio.ms_productos_dyc.repository.MaterialRepository;
import com.microservicio.ms_productos_dyc.repository.ProductoRepository;
import com.microservicio.ms_productos_dyc.repository.TipoProductoRepository;
import com.microservicio.ms_productos_dyc.utilities.ProductoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private MaterialRepository materialRepository;

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
        Producto producto = productoRepository.findByIdWithTipoProducto(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ProductoMapper.toDTO(producto);
    }

    //---------MÉTODOS POST---------//
    @Override
    public ProductoDTO crear(ProductoDTO dto) {
        // validamos que existe el tipo de producto Y está activo
        Long idTipo = dto.getIdTipoProducto();
        TipoProducto tipo = tipoProductoRepository.findById(idTipo)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de Producto no existe: " + idTipo));
        
        if (tipo.getActivo() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de producto está inactivo: " + idTipo);
        }

        Material material = materialRepository.findById(dto.getIdMaterial())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Material no existe: " + dto.getIdMaterial()));
        if (material.getActivo() != 1) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Material inactivo: " + dto.getIdMaterial()
            );
        }

        //Convertimos y guardamos
        Producto producto = ProductoMapper.toEntity(dto);
        producto.setTipoProducto(tipo); //nos aseguramos de forzar la entidad cargada
        producto.setMaterial(material);
        Producto saved = productoRepository.save(producto);
        return ProductoMapper.toDTO(saved);
    }


    //---------MÉTODOS PUT---------//
    @Override
    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Producto existente = productoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Producto no encontrado con id: " + id));

        //Validamos el tipo
        Long idTipo = dto.getIdTipoProducto();
        TipoProducto tipo = tipoProductoRepository.findById(idTipo)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Tipo de Producto no existe: " + idTipo
            ));
        if (tipo.getActivo() != 1) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Tipo de Producto se encuentra inactivo: " +idTipo
            );
        }

        // Validamos el material
        Material material = materialRepository.findById(dto.getIdMaterial())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Material no existe: " + dto.getIdMaterial()
            ));
        if (material.getActivo() != 1) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Material está inactivo: " + dto.getIdMaterial()
            );
        }

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setMedidas(dto.getMedidas());
        existente.setPrecio(dto.getPrecio());
        existente.setUrlImagen(dto.getUrlImagen());
        existente.setActivo(dto.getActivo());
        existente.setTipoProducto(tipo);
        existente.setMaterial(material);

        Producto updated = productoRepository.save(existente);
        return ProductoMapper.toDTO(updated);
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