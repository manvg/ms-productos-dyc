package com.microservicio.ms_productos_dyc.service.MaterialSerice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.ms_productos_dyc.model.dto.MaterialDTO;
import com.microservicio.ms_productos_dyc.model.entity.Material;
import com.microservicio.ms_productos_dyc.repository.MaterialRepository;
import com.microservicio.ms_productos_dyc.utilities.MaterialMapper;

@Service
public class MaterialServiceImpl implements MaterialService{
    
    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<MaterialDTO> obtenerTodos() {
        return materialRepository.findAll()
            .stream()
            .map(MaterialMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public MaterialDTO obtenerPorId(Long id) {
        Material m = materialRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Material no encontrado con ID: " + id
            ));
        return MaterialMapper.toDTO(m);
    }

    @Override
    public MaterialDTO crear(MaterialDTO dto) {
        Material m = MaterialMapper.toEntity(dto);
        m.setIdMaterial(null);
        Material guardado = materialRepository.save(m);
        return MaterialMapper.toDTO(guardado);
    }

    @Override
    public MaterialDTO actualizar(Long id, MaterialDTO dto) {
        Material existente = materialRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Material no encontrado con ID: " + id
            ));
            existente.setNombre(dto.getNombre());
            existente.setActivo(dto.getActivo());
            Material actualizado = materialRepository.save(existente);
            return MaterialMapper.toDTO(actualizado);
    }

    @Override
    public void cambiarEstado(Long id, Integer activo) {
        Material m = materialRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Material no encontrado con ID: " + id
            ));
        m.setActivo(activo);
        materialRepository.save(m);
    }

    @Override
    public void eliminar(Long id) {
        materialRepository.deleteById(id);
    }
}
