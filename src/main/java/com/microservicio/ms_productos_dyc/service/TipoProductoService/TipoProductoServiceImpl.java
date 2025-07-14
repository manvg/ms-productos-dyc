package com.microservicio.ms_productos_dyc.service.TipoProductoService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.ms_productos_dyc.model.dto.TipoProductoDTO;
import com.microservicio.ms_productos_dyc.model.entity.TipoProducto;
import com.microservicio.ms_productos_dyc.repository.TipoProductoRepository;

@Service
public class TipoProductoServiceImpl implements TipoProductoService{
    
    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Override
    public List<TipoProductoDTO> obtenerTodos() {
        return tipoProductoRepository.findAll().stream()
        .map(tp -> new TipoProductoDTO(
            tp.getIdTipoProducto(),
            tp.getNombre(),
            tp.getUrlImagen(),
            tp.getActivo()))
        .collect(Collectors.toList());
    }

    @Override
    public TipoProductoDTO crear(TipoProductoDTO dto) {
        TipoProducto entity = new TipoProducto();
        entity.setNombre(dto.getNombre());
        entity.setUrlImagen(dto.getUrlImagen());
        entity.setActivo(dto.getActivo() != null ? dto.getActivo() : 1);
        tipoProductoRepository.save(entity);
        dto.setIdTipoProducto(entity.getIdTipoProducto());
        return dto;
    }

    @Override
    public TipoProductoDTO actualizar(Long id, TipoProductoDTO dto) {
        TipoProducto entity = tipoProductoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));
        entity.setNombre(dto.getNombre());
        entity.setUrlImagen(dto.getUrlImagen());
        tipoProductoRepository.save(entity);
        return dto;
    }

    @Override
    public void cambiarEstado(Long id, Integer activo) {
        TipoProducto entity = tipoProductoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tipo de producto no encontrado"));
        entity.setActivo(activo);
        tipoProductoRepository.save(entity);
    }

    @Override
    public TipoProductoDTO obtenerPorId(Long id) {
        TipoProducto tp = tipoProductoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No encontrado ID: " + id));
        return new TipoProductoDTO(
        tp.getIdTipoProducto(),
        tp.getNombre(),
        tp.getUrlImagen(),
        tp.getActivo()
        );
    }

    @Override
    public void eliminar(Long id) {
        tipoProductoRepository.deleteById(id);
    }
}
