package com.microservicio.ms_productos_dyc.utilities;

import static org.assertj.core.api.Assertions.assertThat;

import com.microservicio.ms_productos_dyc.model.dto.TipoProductoDTO;
import com.microservicio.ms_productos_dyc.model.entity.TipoProducto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TipoProductoMapperTest {

    @Test
    @DisplayName("toDTO null → null")
    void toDTO_null() {
        assertThat(TipoProductoMapper.toDTO(null)).isNull();
    }

    @Test
    @DisplayName("toDTO mapea todos los campos correctamente")
    void toDTO_full() {
        TipoProducto entity = new TipoProducto();
        entity.setIdTipoProducto(42L);
        entity.setNombre("Categoría X");
        entity.setUrlImagen("http://imagen");
        entity.setActivo(0);

        TipoProductoDTO dto = TipoProductoMapper.toDTO(entity);
        assertThat(dto).isNotNull();
        assertThat(dto.getIdTipoProducto()).isEqualTo(42L);
        assertThat(dto.getNombre()).isEqualTo("Categoría X");
        assertThat(dto.getUrlImagen()).isEqualTo("http://imagen");
        assertThat(dto.getActivo()).isEqualTo(0);
    }

    @Test
    @DisplayName("toEntity null → null")
    void toEntity_null() {
        assertThat(TipoProductoMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("toEntity mapea todos los campos correctamente cuando activo presente")
    void toEntity_full() {
        TipoProductoDTO dto = new TipoProductoDTO(7L, "Cat Y", "urlY", 1);
        TipoProducto entity = TipoProductoMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getIdTipoProducto()).isEqualTo(7L);
        assertThat(entity.getNombre()).isEqualTo("Cat Y");
        assertThat(entity.getUrlImagen()).isEqualTo("urlY");
        assertThat(entity.getActivo()).isEqualTo(1);
    }

    @Test
    @DisplayName("toEntity usa 1 como activo por defecto cuando dto.activo es null")
    void toEntity_defaultActivo() {
        TipoProductoDTO dto = new TipoProductoDTO();
        dto.setIdTipoProducto(9L);
        dto.setNombre("Cat Z");
        dto.setUrlImagen("urlZ");
        dto.setActivo(null);

        TipoProducto entity = TipoProductoMapper.toEntity(dto);
        assertThat(entity.getActivo()).isEqualTo(1);
    }
}
