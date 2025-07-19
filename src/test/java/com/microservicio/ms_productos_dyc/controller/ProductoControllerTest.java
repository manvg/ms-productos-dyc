package com.microservicio.ms_productos_dyc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.ms_productos_dyc.model.dto.ProductoDTO;
import com.microservicio.ms_productos_dyc.model.dto.ResponseModelDTO;
import com.microservicio.ms_productos_dyc.service.ProductoService;
import com.microservicio.ms_productos_dyc.utilities.MensajesProducto;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("GET /api/productos -> 200 + lista vacía")
    void testObtenerTodos() throws Exception {
        Mockito.when(productoService.obtenerTodos()).thenReturn(List.of());
        mvc.perform(get("/api/productos"))
           .andExpect(status().isOk())
           .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("POST /api/productos → 200 + mensaje creado")
    void testCrearProducto() throws Exception {
        // Construimos el DTO de entrada:
        ProductoDTO dtoIn = new ProductoDTO();
        dtoIn.setNombre("A");
        dtoIn.setDescripcion("Desc");
        dtoIn.setMedidas("10x10");
        dtoIn.setPrecio(BigDecimal.valueOf(5));
        dtoIn.setUrlImagen("url");
        dtoIn.setActivo(1);
        dtoIn.setIdTipoProducto(1L);
        dtoIn.setIdMaterial(2L);

        // Y el DTO que devolverá el stub del servicio:
        ProductoDTO dtoOut = new ProductoDTO();
        dtoOut.setIdProducto(123L);
        dtoOut.setNombre(dtoIn.getNombre());
        // (rellena los demás campos si los necesitas en el test)

        // Stub: cuando llamen a productoService.crear(...) devuelve dtoOut
        when(productoService.crear(any(ProductoDTO.class)))
            .thenReturn(dtoOut);

        mvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoIn)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(true))
        .andExpect(jsonPath("$.message").value(MensajesProducto.CREADO));

        // Opcional: verificar que el servicio recibió el DTO correcto
        ArgumentCaptor<ProductoDTO> captor = ArgumentCaptor.forClass(ProductoDTO.class);
        verify(productoService).crear(captor.capture());
        assertEquals("A", captor.getValue().getNombre());
    }
    @Test
    @DisplayName("PUT /api/productos/{id}/cambiar-estado invalido → 400")
    void testCambiarEstadoBad() throws Exception {
        mvc.perform(put("/api/productos/1/cambiar-estado")
                .param("activo", "2"))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("$.status").value(false))
           .andExpect(jsonPath("$.message").value(MensajesProducto.ESTADO_INVALIDO));
    }

     @Test
    @DisplayName("DELETE /api/productos/{id} → 200 + mensaje eliminado")
    void testEliminar() throws Exception {
        Mockito.doNothing().when(productoService).eliminar(1L);
        mvc.perform(delete("/api/productos/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value(MensajesProducto.ELIMINADO));
    }    


}
