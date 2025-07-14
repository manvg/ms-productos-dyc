package com.microservicio.ms_productos_dyc.service.servicio;

import com.microservicio.ms_productos_dyc.model.dto.ServicioDTO;
import com.microservicio.ms_productos_dyc.model.entity.Servicio;
import com.microservicio.ms_productos_dyc.repository.ServicioRepository;
import com.microservicio.ms_productos_dyc.utilities.ServicioMapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServicioServiceImpl implements ServicioService{
    
    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public List<ServicioDTO> obtenerTodos() {
        return servicioRepository.findAll().stream()
                    .map(ServicioMapper::toDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public List<ServicioDTO> obtenerActivos() {
        return servicioRepository.findByActivo(1).stream()
                .map(ServicioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServicioDTO obtenerPorId(Long id) {
        Servicio s = servicioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Servicio no encontrado con id: " + id));
        return ServicioMapper.toDTO(s);
    }

    @Override
    public ServicioDTO crear(ServicioDTO dto) {
        Servicio s = ServicioMapper.toEntity(dto);
        Servicio saved = servicioRepository.save(s);
        return ServicioMapper.toDTO(saved);
    }

    @Override
    public ServicioDTO actualizar(Long id, ServicioDTO dto) {
        Servicio existente = servicioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Servicio no encontrado con id: " + id));

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setPrecio(dto.getPrecio());
        existente.setUrlImagen(dto.getUrlImagen());
        existente.setActivo(dto.getActivo());

        Servicio updated = servicioRepository.save(existente);
        return ServicioMapper.toDTO(updated);
    }

    @Override
    public void cambiarEstado(Long id, Integer activo) {
        Servicio s = servicioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Servicio no encontrado con id: " + id));
        s.setActivo(activo);
        servicioRepository.save(s);
    }

    @Override
    public void eliminar(Long id) {
        servicioRepository.deleteById(id);
    }
}