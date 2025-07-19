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

import com.microservicio.ms_productos_dyc.model.dto.MaterialDTO;
import com.microservicio.ms_productos_dyc.model.dto.ResponseModelDTO;
import com.microservicio.ms_productos_dyc.service.MaterialSerice.MaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(MaterialController.class)
class MaterialControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MaterialService materialService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("GET /api/materiales → 200 + lista vacía")
    void testObtenerTodos() throws Exception {
        when(materialService.obtenerTodos()).thenReturn(List.of());

        mvc.perform(get("/api/materiales")
                .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("GET /api/materiales/sans → 200 + mensaje custom")
    void testSans() throws Exception {
        mvc.perform(get("/api/materiales/sans"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Te pica la patita.com"));
    }

    @Test
    @DisplayName("GET /api/materiales/{id} → 200 + datos material")
    void testObtenerPorId() throws Exception {
        MaterialDTO dto = new MaterialDTO(5L, "Madera", 1);
        when(materialService.obtenerPorId(5L)).thenReturn(dto);

        mvc.perform(get("/api/materiales/5")
                .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.idMaterial").value(5))
           .andExpect(jsonPath("$.nombre").value("Madera"))
           .andExpect(jsonPath("$.activo").value(1));
    }

    @Test
    @DisplayName("POST /api/materiales → 200 + mensaje creado")
    void testCrearMaterial() throws Exception {
        MaterialDTO dto = new MaterialDTO();
        dto.setNombre("Metal");
        dto.setActivo(1);

        // el servicio devuelve un DTO con id asignado
        MaterialDTO created = new MaterialDTO(10L, "Metal", 1);
        when(materialService.crear(any(MaterialDTO.class))).thenReturn(created);

        mvc.perform(post("/api/materiales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Material creado correctamente"));
    }

    @Test
    @DisplayName("PUT /api/materiales/{id} → 200 + mensaje actualizado")
    void testActualizarMaterial() throws Exception {
        MaterialDTO dto = new MaterialDTO();
        dto.setNombre("Plástico");
        dto.setActivo(1);

        when(materialService.actualizar(anyLong(), any(MaterialDTO.class)))
            .thenReturn(new MaterialDTO(3L, "Plástico", 1));

        mvc.perform(put("/api/materiales/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Material actualizado corectamente"));
    }

    @Test
    @DisplayName("PUT /api/materiales/{id}/cambiar-estado invalido → 400")
    void testCambiarEstadoBad() throws Exception {
        mvc.perform(put("/api/materiales/1/cambiar-estado")
                .param("activo", "5"))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("$.status").value(false))
           .andExpect(jsonPath("$.message").value("El parámetro 'activo' debe ser 0 o 1"));
    }

    @Test
    @DisplayName("PUT /api/materiales/{id}/cambiar-estado valido → 200 + mensaje")
    void testCambiarEstadoOk() throws Exception {
        doNothing().when(materialService).cambiarEstado(4L, 0);

        mvc.perform(put("/api/materiales/4/cambiar-estado")
                .param("activo", "0"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Material desactivado"));
    }

    @Test
    @DisplayName("DELETE /api/materiales/{id} → 200 + mensaje eliminado")
    void testEliminarMaterial() throws Exception {
        doNothing().when(materialService).eliminar(7L);

        mvc.perform(delete("/api/materiales/7"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Material eliminado correctamente"));
    }
}
