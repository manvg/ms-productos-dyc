package com.microservicio.ms_productos_dyc.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.ms_productos_dyc.model.dto.ProductoDTO;
import com.microservicio.ms_productos_dyc.model.entity.Material;
import com.microservicio.ms_productos_dyc.model.entity.TipoProducto;
import com.microservicio.ms_productos_dyc.repository.MaterialRepository;
import com.microservicio.ms_productos_dyc.repository.ProductoRepository;
import com.microservicio.ms_productos_dyc.repository.TipoProductoRepository;


@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

    @Mock private ProductoRepository productoRepo;
    @Mock private TipoProductoRepository tipoRepo;
    @Mock private MaterialRepository materialRepo;

    @InjectMocks
    private ProductoServiceImpl service;
    private ProductoDTO dto;

    @BeforeEach
    void setup() {
        dto = new ProductoDTO();
        dto.setNombre("Test");
        dto.setDescripcion("Desc");
        dto.setMedidas("10x10");
        dto.setPrecio(new BigDecimal("5.00"));
        dto.setUrlImagen("http://img.jpg");
        dto.setActivo(1);
        dto.setIdTipoProducto(1L);
        dto.setIdMaterial(2L);
    }

    @Test
    void crear_conTipoYMaterialValidos_guardaYRetornaDTO() {
         // given
        TipoProducto tp = new TipoProducto();
        tp.setIdTipoProducto(1L);
        tp.setActivo(1);
        when(tipoRepo.findById(1L)).thenReturn(Optional.of(tp));

        Material m = new Material();
        m.setIdMaterial(2L);
        m.setActivo(1);
        when(materialRepo.findById(2L)).thenReturn(Optional.of(m));

        when(productoRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        ProductoDTO result = service.crear(dto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getIdTipoProducto()).isEqualTo(1L);
        assertThat(result.getIdMaterial()).isEqualTo(2L);

        ArgumentCaptor<com.microservicio.ms_productos_dyc.model.entity.Producto> captor =
            ArgumentCaptor.forClass(com.microservicio.ms_productos_dyc.model.entity.Producto.class);
        verify(productoRepo).save(captor.capture());
        assertThat(captor.getValue().getTipoProducto().getIdTipoProducto()).isEqualTo(1L);
        assertThat(captor.getValue().getMaterial().getIdMaterial()).isEqualTo(2L);
    }

    @Test
    void crear_conTipoInvalido_lanzaBadRequest() {
        // tipoRepo devuelve vacío
        when(tipoRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.crear(dto))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Tipo de Producto no existe");
    }

    @Test
    void crear_conMaterialInactivo_lanzaBadRequest() {
        TipoProducto tp = new TipoProducto();
        tp.setIdTipoProducto(1L);
        tp.setActivo(1);
        when(tipoRepo.findById(1L)).thenReturn(Optional.of(tp));

        Material m = new Material();
        m.setIdMaterial(2L);
        m.setActivo(0);
        when(materialRepo.findById(2L)).thenReturn(Optional.of(m));

        assertThatThrownBy(() -> service.crear(dto))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Material inactivo");
    }
    
}
