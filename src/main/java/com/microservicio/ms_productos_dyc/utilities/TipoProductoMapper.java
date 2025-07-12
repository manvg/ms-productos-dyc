package com.microservicio.ms_productos_dyc.utilities;

import com.microservicio.ms_productos_dyc.model.dto.TipoProductoDTO;
import com.microservicio.ms_productos_dyc.model.entity.TipoProducto;

public class TipoProductoMapper {
    public static TipoProductoDTO toDTO(TipoProducto e) {
        if (e == null) return null;
        return new TipoProductoDTO(
            e.getIdTipoProducto(),
            e.getNombre(),
            e.getUrlImagen(),
            e.getActivo()
        );
    }

    public static TipoProducto toEntity(TipoProductoDTO dto) {
        if (dto == null) return null;
        TipoProducto e = new TipoProducto();
        e.setIdTipoProducto(dto.getIdTipoProducto());
        e.setNombre(dto.getNombre());
        e.setUrlImagen(dto.getUrlImagen());
        e.setActivo(dto.getActivo() != null ? dto.getActivo() : 1);
        return e;
    }
}