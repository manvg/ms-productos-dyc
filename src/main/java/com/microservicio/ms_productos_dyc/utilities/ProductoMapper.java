package com.microservicio.ms_productos_dyc.utilities;

import com.microservicio.ms_productos_dyc.model.dto.ProductoDTO;
import com.microservicio.ms_productos_dyc.model.entity.Producto;
import com.microservicio.ms_productos_dyc.model.entity.TipoProducto;

public class ProductoMapper {

    public static ProductoDTO toDTO(Producto p) {
        if (p == null) return null;

        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(p.getIdProducto());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setMaterial(p.getMaterial());
        dto.setMedidas(p.getMedidas());
        dto.setPrecio(p.getPrecio());
        dto.setUrlImagen(p.getUrlImagen());
        dto.setActivo(p.getActivo());

        if (p.getTipoProducto() != null) {
            dto.setIdTipoProducto(p.getTipoProducto().getIdTipoProducto());
        }

        return dto;
    }

    public static Producto toEntity(ProductoDTO dto) {
        if (dto == null) return null;

        Producto p = new Producto();
        p.setIdProducto(dto.getIdProducto());
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setMaterial(dto.getMaterial());
        p.setMedidas(dto.getMedidas());
        p.setPrecio(dto.getPrecio());
        p.setUrlImagen(dto.getUrlImagen());
        p.setActivo(dto.getActivo());

        if (dto.getIdTipoProducto() != null) {
            TipoProducto tipo = new TipoProducto();
            tipo.setIdTipoProducto(dto.getIdTipoProducto());
            p.setTipoProducto(tipo);
        }

        return p;
    }
}