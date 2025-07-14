package com.microservicio.ms_productos_dyc.utilities;

import com.microservicio.ms_productos_dyc.model.dto.ServicioDTO;
import com.microservicio.ms_productos_dyc.model.entity.Servicio;

public class ServicioMapper {

    public static ServicioDTO toDTO(Servicio s) {
        if (s == null) return null;
        ServicioDTO dto = new ServicioDTO();
        dto.setIdServicio(s.getIdServicio());
        dto.setNombre(s.getNombre());
        dto.setDescripcion(s.getDescripcion());
        dto.setPrecio(s.getPrecio());
        dto.setUrlImagen(s.getUrlImagen());
        dto.setActivo(s.getActivo());
        return dto;
    }

    public static Servicio toEntity(ServicioDTO dto) {
        if (dto == null) return null;
        Servicio s = new Servicio();
        if (dto.getIdServicio() != null && dto.getIdServicio() > 0) {
            s.setIdServicio(dto.getIdServicio());
        }
        s.setNombre(dto.getNombre());
        s.setDescripcion(dto.getDescripcion());
        s.setPrecio(dto.getPrecio());
        s.setUrlImagen(dto.getUrlImagen());
        if (dto.getActivo() != null) {
            s.setActivo(dto.getActivo());
        }
        return s;
    }
}