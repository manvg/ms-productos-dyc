package com.microservicio.ms_productos_dyc.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.microservicio.ms_productos_dyc.model.dto.TipoProductoDTO;
import com.microservicio.ms_productos_dyc.model.dto.ResponseModelDTO;
import com.microservicio.ms_productos_dyc.service.TipoProductoService.TipoProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(TipoProductoController.class)
class TipoProductoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TipoProductoService tipoService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("GET /api/productos/tipos → 200 + lista vacía")
    void testObtenerTodos_empty() throws Exception {
        when(tipoService.obtenerTodos()).thenReturn(List.of());

        mvc.perform(get("/api/productos/tipos")
                .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("GET /api/productos/tipos → 200 + lista con elementos")
    void testObtenerTodos_nonEmpty() throws Exception {
        TipoProductoDTO a = new TipoProductoDTO(1L, "Cat A", "http://imgA", 1);
        when(tipoService.obtenerTodos()).thenReturn(List.of(a));

        mvc.perform(get("/api/productos/tipos")
                .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].idTipoProducto").value(1))
           .andExpect(jsonPath("$[0].nombre").value("Cat A"))
           .andExpect(jsonPath("$[0].urlImagen").value("http://imgA"))
           .andExpect(jsonPath("$[0].activo").value(1));
    }

    @Test
    @DisplayName("GET /api/productos/tipos/{id} → 200 + datos correctos")
    void testObtenerPorId() throws Exception {
        TipoProductoDTO dto = new TipoProductoDTO(5L, "Cat X", "http://imgX", 0);
        when(tipoService.obtenerPorId(5L)).thenReturn(dto);

        mvc.perform(get("/api/productos/tipos/5")
                .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.idTipoProducto").value(5))
           .andExpect(jsonPath("$.nombre").value("Cat X"))
           .andExpect(jsonPath("$.urlImagen").value("http://imgX"))
           .andExpect(jsonPath("$.activo").value(0));
    }

    @Test
    @DisplayName("POST /api/productos/tipos → 200 + mensaje creado")
    void testCrear_ok() throws Exception {
        TipoProductoDTO dtoIn = new TipoProductoDTO(null, "Nueva", "http://nueva", 1);
        TipoProductoDTO dtoOut = new TipoProductoDTO(10L, "Nueva", "http://nueva", 1);
        when(tipoService.crear(any(TipoProductoDTO.class))).thenReturn(dtoOut);

        mvc.perform(post("/api/productos/tipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoIn)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Tipo de producto creado correctamente"));
    }

    @Test
    @DisplayName("POST /api/productos/tipos → 400 por validación (nombre vacío)")
    void testCrear_badValidation() throws Exception {
        TipoProductoDTO dtoBad = new TipoProductoDTO();
        // nombre y urlImagen faltan → @NotBlank
        mvc.perform(post("/api/productos/tipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoBad)))
           .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/productos/tipos/{id} → 200 + mensaje actualizado")
    void testActualizar_ok() throws Exception {
        TipoProductoDTO dtoIn = new TipoProductoDTO(null, "Upd", "http://upd", 1);
        when(tipoService.actualizar(anyLong(), any(TipoProductoDTO.class)))
            .thenReturn(new TipoProductoDTO(3L, "Upd", "http://upd", 1));

        mvc.perform(put("/api/productos/tipos/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoIn)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Tipo de producto actualizado correctamente"));
    }

    @Test
    @DisplayName("PUT /api/productos/tipos/{id}/cambiar-estado → 200 + mensaje actualizado")
    void testCambiarEstado() throws Exception {
        doNothing().when(tipoService).cambiarEstado(4L, 0);

        mvc.perform(put("/api/productos/tipos/4/cambiar-estado")
                .param("activo", "0"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Estado actualizado"));
    }

    @Test
    @DisplayName("DELETE /api/productos/tipos/{id} → 200 + mensaje eliminado")
    void testEliminar() throws Exception {
        doNothing().when(tipoService).eliminar(7L);

        mvc.perform(delete("/api/productos/tipos/7"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Tipo producto eliminado correctamente"));
    }
}
