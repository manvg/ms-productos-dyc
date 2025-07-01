package com.microservicio.ms_productos_dyc.utilities;

import com.microservicio.ms_productos_dyc.model.dto.MaterialDTO;
import com.microservicio.ms_productos_dyc.model.entity.Material;

public class MaterialMapper {
    public static MaterialDTO toDTO(Material m) {
        if (m == null) return null;
        return new MaterialDTO(
            m.getIdMaterial(),
            m.getNombre(),
            m.getActivo()
        );
    }

    public static Material toEntity(MaterialDTO dto) {
        if (dto == null) return null;
        Material m = new Material();
        m.setIdMaterial(dto.getIdMaterial());
        m.setNombre(dto.getNombre());
        m.setActivo(dto.getActivo());
        return m;
    }
    
}
