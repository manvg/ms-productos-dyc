package com.microservicio.ms_productos_dyc.utilities;

import com.microservicio.ms_productos_dyc.model.dto.ProductoDTO;
import com.microservicio.ms_productos_dyc.model.dto.ProductoDetalleDTO;
import com.microservicio.ms_productos_dyc.model.dto.ProductoSumarioDTO;
import com.microservicio.ms_productos_dyc.model.entity.Material;
import com.microservicio.ms_productos_dyc.model.entity.Producto;
import com.microservicio.ms_productos_dyc.model.entity.TipoProducto;

public class ProductoMapper {

    public static ProductoDTO toDTO(Producto p) {
        if (p == null) return null;

        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(p.getIdProducto());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setMedidas(p.getMedidas());
        dto.setPrecio(p.getPrecio());
        dto.setUrlImagen(p.getUrlImagen());
        dto.setActivo(p.getActivo());

        if (p.getTipoProducto() != null) {
            dto.setIdTipoProducto(p.getTipoProducto().getIdTipoProducto());
            dto.setNombreTipoProducto(p.getTipoProducto().getNombre()); // <<--- Agrega esto
        }

        if (p.getMaterial() != null) { //new 30/06/25
            dto.setIdMaterial(p.getMaterial().getIdMaterial()); 
            dto.setNombreMaterial(p.getMaterial().getNombre()); 
        }

        return dto;
    }

    public static Producto toEntity(ProductoDTO dto) {
        if (dto == null) return null;

        Producto p = new Producto();
        p.setIdProducto(dto.getIdProducto());
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setMedidas(dto.getMedidas());
        p.setPrecio(dto.getPrecio());
        p.setUrlImagen(dto.getUrlImagen());
        
        if (dto.getActivo() != null) {
            p.setActivo(dto.getActivo());
        }

        if (dto.getIdTipoProducto() != null) {
            TipoProducto tipo = new TipoProducto();
            tipo.setIdTipoProducto(dto.getIdTipoProducto());
            p.setTipoProducto(tipo);
        }

        if (dto.getIdMaterial() != null){ //new 30/06/25
            Material m = new Material();
            m.setIdMaterial(dto.getIdMaterial());
            p.setMaterial(m);
        }

        return p;
    }

    //pSumario
    public static ProductoSumarioDTO toSumarioDTO(Producto p) {
        if (p == null) return null;
        return new ProductoSumarioDTO(
            p.getIdProducto(),
            p.getNombre(),
            p.getPrecio(),
            p.getUrlImagen()
        );
    }

    public static ProductoDetalleDTO toDetalleDTO(Producto p) {
        if (p == null) return null;
        ProductoDetalleDTO dto = new ProductoDetalleDTO();
        dto.setIdProducto(p.getIdProducto());
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());
        dto.setUrlImagen(p.getUrlImagen());
        dto.setDescripcion(p.getDescripcion());
        dto.setMedidas(p.getMedidas());
        String nombreMat = (p.getMaterial() != null) ? p.getMaterial().getNombre() : null;
        dto.setNombreMaterial(nombreMat);
        return dto;
    }
}
