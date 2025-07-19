package com.microservicio.ms_productos_dyc.utilities;

import static org.assertj.core.api.Assertions.assertThat;

import com.microservicio.ms_productos_dyc.model.dto.MaterialDTO;
import com.microservicio.ms_productos_dyc.model.entity.Material;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaterialMapperTest {

    @Test
    @DisplayName("toDTO null → null")
    void toDTO_null() {
        assertThat(MaterialMapper.toDTO(null)).isNull();
    }

    @Test
    @DisplayName("toDTO mapea todos los campos correctamente")
    void toDTO_full() {
        Material entity = new Material();
        entity.setIdMaterial(15L);
        entity.setNombre("Acero");
        entity.setActivo(0);

        MaterialDTO dto = MaterialMapper.toDTO(entity);
        assertThat(dto).isNotNull();
        assertThat(dto.getIdMaterial()).isEqualTo(15L);
        assertThat(dto.getNombre()).isEqualTo("Acero");
        assertThat(dto.getActivo()).isEqualTo(0);
    }

    @Test
    @DisplayName("toEntity null → null")
    void toEntity_null() {
        assertThat(MaterialMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("toEntity mapea todos los campos correctamente")
    void toEntity_full() {
        MaterialDTO dto = new MaterialDTO();
        dto.setIdMaterial(20L);
        dto.setNombre("Plástico");
        dto.setActivo(1);

        Material entity = MaterialMapper.toEntity(dto);
        assertThat(entity).isNotNull();
        assertThat(entity.getIdMaterial()).isEqualTo(20L);
        assertThat(entity.getNombre()).isEqualTo("Plástico");
        assertThat(entity.getActivo()).isEqualTo(1);
    }
}
