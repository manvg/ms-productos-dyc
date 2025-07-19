package com.microservicio.ms_productos_dyc.utilities;

import static org.assertj.core.api.Assertions.assertThat;

import com.microservicio.ms_productos_dyc.model.dto.ProductoDTO;
import com.microservicio.ms_productos_dyc.model.dto.ProductoDetalleDTO;
import com.microservicio.ms_productos_dyc.model.dto.ProductoSumarioDTO;
import com.microservicio.ms_productos_dyc.model.entity.Material;
import com.microservicio.ms_productos_dyc.model.entity.Producto;
import com.microservicio.ms_productos_dyc.model.entity.TipoProducto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductoMapperTest {

    @Test
    @DisplayName("toDTO null → null")
    void toDTO_null() {
        assertThat(ProductoMapper.toDTO(null)).isNull();
    }

    @Test
    @DisplayName("toDTO mapea todos los campos correctamente")
    void toDTO_full() {
        // entidad con tipo y material
        TipoProducto tp = new TipoProducto();
        tp.setIdTipoProducto(2L);
        tp.setNombre("Cat");
        Material mat = new Material();
        mat.setIdMaterial(3L);
        mat.setNombre("Madera");

        Producto p = new Producto();
        p.setIdProducto(1L);
        p.setNombre("ProductoX");
        p.setDescripcion("DescX");
        p.setMedidas("5x5");
        p.setPrecio(new BigDecimal("9.99"));
        p.setUrlImagen("http://img");
        p.setActivo(1);
        p.setTipoProducto(tp);
        p.setMaterial(mat);

        ProductoDTO dto = ProductoMapper.toDTO(p);
        assertThat(dto).isNotNull();
        assertThat(dto.getIdProducto()).isEqualTo(1L);
        assertThat(dto.getNombre()).isEqualTo("ProductoX");
        assertThat(dto.getDescripcion()).isEqualTo("DescX");
        assertThat(dto.getMedidas()).isEqualTo("5x5");
        assertThat(dto.getPrecio()).isEqualByComparingTo("9.99");
        assertThat(dto.getUrlImagen()).isEqualTo("http://img");
        assertThat(dto.getActivo()).isEqualTo(1);
        assertThat(dto.getIdTipoProducto()).isEqualTo(2L);
        assertThat(dto.getNombreTipoProducto()).isEqualTo("Cat");
        assertThat(dto.getIdMaterial()).isEqualTo(3L);
        assertThat(dto.getNombreMaterial()).isEqualTo("Madera");
    }

    @Test
    @DisplayName("toEntity null → null")
    void toEntity_null() {
        assertThat(ProductoMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("toEntity mapea todos los campos correctamente")
    void toEntity_full() {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(5L);
        dto.setNombre("ProdY");
        dto.setDescripcion("DescY");
        dto.setMedidas("2x2");
        dto.setPrecio(new BigDecimal("3.50"));
        dto.setUrlImagen("urlY");
        dto.setActivo(0);
        dto.setIdTipoProducto(7L);
        dto.setIdMaterial(8L);

        Producto p = ProductoMapper.toEntity(dto);
        assertThat(p).isNotNull();
        assertThat(p.getIdProducto()).isEqualTo(5L);
        assertThat(p.getNombre()).isEqualTo("ProdY");
        assertThat(p.getDescripcion()).isEqualTo("DescY");
        assertThat(p.getMedidas()).isEqualTo("2x2");
        assertThat(p.getPrecio()).isEqualByComparingTo("3.50");
        assertThat(p.getUrlImagen()).isEqualTo("urlY");
        assertThat(p.getActivo()).isEqualTo(0);
        assertThat(p.getTipoProducto()).isNotNull();
        assertThat(p.getTipoProducto().getIdTipoProducto()).isEqualTo(7L);
        assertThat(p.getMaterial()).isNotNull();
        assertThat(p.getMaterial().getIdMaterial()).isEqualTo(8L);
    }

    @Test
    @DisplayName("toSumarioDTO null → null")
    void toSumarioDTO_null() {
        assertThat(ProductoMapper.toSumarioDTO(null)).isNull();
    }

    @Test
    @DisplayName("toSumarioDTO mapea correctamente")
    void toSumarioDTO_full() {
        Producto p = new Producto();
        p.setIdProducto(10L);
        p.setNombre("Sum");
        p.setPrecio(new BigDecimal("1.23"));
        p.setUrlImagen("u");

        ProductoSumarioDTO s = ProductoMapper.toSumarioDTO(p);
        assertThat(s).isNotNull();
        assertThat(s.getIdProducto()).isEqualTo(10L);
        assertThat(s.getNombre()).isEqualTo("Sum");
        assertThat(s.getPrecio()).isEqualByComparingTo("1.23");
        assertThat(s.getUrlImagen()).isEqualTo("u");
    }

    @Test
    @DisplayName("toDetalleDTO null → null")
    void toDetalleDTO_null() {
        assertThat(ProductoMapper.toDetalleDTO(null)).isNull();
    }

    @Test
    @DisplayName("toDetalleDTO mapea correctamente")
    void toDetalleDTO_full() {
        Material mat = new Material(); mat.setNombre("Mtl");
        Producto p = new Producto();
        p.setIdProducto(11L);
        p.setNombre("Det");
        p.setPrecio(new BigDecimal("4.56"));
        p.setUrlImagen("imgD");
        p.setDescripcion("DescD");
        p.setMedidas("7x7");
        p.setMaterial(mat);

        ProductoDetalleDTO d = ProductoMapper.toDetalleDTO(p);
        assertThat(d).isNotNull();
        assertThat(d.getIdProducto()).isEqualTo(11L);
        assertThat(d.getNombre()).isEqualTo("Det");
        assertThat(d.getPrecio()).isEqualByComparingTo("4.56");
        assertThat(d.getUrlImagen()).isEqualTo("imgD");
        assertThat(d.getDescripcion()).isEqualTo("DescD");
        assertThat(d.getMedidas()).isEqualTo("7x7");
        assertThat(d.getNombreMaterial()).isEqualTo("Mtl");
    }
}
