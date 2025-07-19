package com.microservicio.ms_productos_dyc.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.microservicio.ms_productos_dyc.model.dto.ProductoDetalleDTO;
import com.microservicio.ms_productos_dyc.model.dto.ProductoSumarioDTO;
import com.microservicio.ms_productos_dyc.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ExpositorController.class)
class ExpositorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("GET /api/expositor/sumario → 200 + lista de sumarios")
    void testListarSumario() throws Exception {
        // stub del servicio
        List<ProductoSumarioDTO> lista = List.of(
            new ProductoSumarioDTO(1L, "Prod A", BigDecimal.valueOf(10.5), "urlA"),
            new ProductoSumarioDTO(2L, "Prod B", BigDecimal.valueOf(20),   "urlB")
        );
        when(productoService.listarSumario()).thenReturn(lista);

        mvc.perform(get("/api/expositor/sumario")
                .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("$[0].idProducto").value(1))
           .andExpect(jsonPath("$[0].nombre").value("Prod A"))
           .andExpect(jsonPath("$[1].idProducto").value(2))
           .andExpect(jsonPath("$[1].precio").value(20.0));
    }

    @Test
    @DisplayName("GET /api/expositor/{id}/detalle → 200 + detalle correcto")
    void testDetalle() throws Exception {
        ProductoDetalleDTO det = new ProductoDetalleDTO();
        det.setIdProducto(5L);
        det.setNombre("DetalleX");
        det.setPrecio(BigDecimal.valueOf(7.25));
        det.setUrlImagen("urlX");
        det.setDescripcion("DescX");
        det.setMedidas("5x5");
        det.setNombreMaterial("Madera");

        when(productoService.obtenerDetalle(anyLong())).thenReturn(det);

        mvc.perform(get("/api/expositor/5/detalle")
                .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("$.idProducto").value(5))
           .andExpect(jsonPath("$.nombre").value("DetalleX"))
           .andExpect(jsonPath("$.descripcion").value("DescX"))
           .andExpect(jsonPath("$.nombreMaterial").value("Madera"));
    }
}
