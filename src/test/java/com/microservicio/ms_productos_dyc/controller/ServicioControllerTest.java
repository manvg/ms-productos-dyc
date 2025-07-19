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

import com.microservicio.ms_productos_dyc.model.dto.ResponseModelDTO;
import com.microservicio.ms_productos_dyc.model.dto.ServicioDTO;
import com.microservicio.ms_productos_dyc.service.servicio.ServicioService;
import com.microservicio.ms_productos_dyc.utilities.MensajesServicio;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

@WebMvcTest(ServicioController.class)
class ServicioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServicioService servicioService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("GET /api/servicios → 200 + lista vacía")
    void obtenerTodos_empty() throws Exception {
        when(servicioService.obtenerTodos()).thenReturn(List.of());

        mvc.perform(get("/api/servicios")
                .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("GET /api/servicios/activos → 200 + lista de activos")
    void obtenerActivos() throws Exception {
        // Creamos el DTO vía setters
        ServicioDTO s = new ServicioDTO();
        s.setIdServicio(1L);
        s.setNombre("S1");
        s.setDescripcion("Desc");
        s.setPrecio(BigDecimal.valueOf(15));
        s.setActivo(1);

        when(servicioService.obtenerActivos()).thenReturn(List.of(s));

        mvc.perform(get("/api/servicios/activos")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].idServicio").value(1))
        .andExpect(jsonPath("$[0].nombre").value("S1"));
    }

    @Test
    @DisplayName("POST /api/servicios → 200 + mensaje creado")
    void crear_servicio_ok() throws Exception {
        ServicioDTO dto = new ServicioDTO();
        dto.setNombre("Nuevo");
        dto.setDescripcion("Desc");
        dto.setPrecio(BigDecimal.TEN);
        dto.setActivo(1);

        when(servicioService.crear(any(ServicioDTO.class))).thenReturn(dto);

        mvc.perform(post("/api/servicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value(MensajesServicio.CREADO));
    }

    @Test
    @DisplayName("POST /api/servicios → 400 por validación")
    void crear_servicio_bad() throws Exception {
        ServicioDTO dto = new ServicioDTO();
        // nombre en blanco => @NotBlank fallará
        mvc.perform(post("/api/servicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
           .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/servicios/{id} → 200 + mensaje actualizado")
    void actualizar_servicio_ok() throws Exception {
        ServicioDTO dto = new ServicioDTO();
        dto.setNombre("Upd");
        dto.setDescripcion("DescUp");
        dto.setPrecio(BigDecimal.ONE);
        dto.setActivo(1);

        when(servicioService.actualizar(anyLong(), any(ServicioDTO.class))).thenReturn(dto);

        mvc.perform(put("/api/servicios/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value(MensajesServicio.ACTUALIZADO));
    }

    @Test
    @DisplayName("PUT /api/servicios/{id}/cambiar-estado → 200 + mensaje")
    void cambiarEstado_ok() throws Exception {
        doNothing().when(servicioService).cambiarEstado(3L, 0);

        mvc.perform(put("/api/servicios/3/cambiar-estado")
                .param("activo", "0"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value(MensajesServicio.ACTIVADO));
    }

    @Test
    @DisplayName("GET /api/servicios/{id} → 200 + datos servicio")
    void obtenerPorId() throws Exception {
        ServicioDTO dto = new ServicioDTO();
        dto.setIdServicio(4L);
        dto.setNombre("X");
        dto.setDescripcion("DescX");
        dto.setPrecio(BigDecimal.valueOf(4.5));
        dto.setActivo(1);

        when(servicioService.obtenerPorId(4L)).thenReturn(dto);

        mvc.perform(get("/api/servicios/4"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.idServicio").value(4))
        .andExpect(jsonPath("$.nombre").value("X"));
    }

    @Test
    @DisplayName("DELETE /api/servicios/{id} → 200 + mensaje eliminado")
    void eliminar_servicio() throws Exception {
        doNothing().when(servicioService).eliminar(5L);

        mvc.perform(delete("/api/servicios/5"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value(MensajesServicio.ELIMINADO));
    }
}
