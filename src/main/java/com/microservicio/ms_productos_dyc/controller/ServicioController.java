package com.microservicio.ms_productos_dyc.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.ms_productos_dyc.model.dto.ResponseModelDTO;
import com.microservicio.ms_productos_dyc.model.dto.ServicioDTO;
import com.microservicio.ms_productos_dyc.service.servicio.ServicioService;
import com.microservicio.ms_productos_dyc.utilities.MensajesServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<ServicioDTO>> obtenerTodos() {
        return ResponseEntity.ok(servicioService.obtenerTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ServicioDTO>> obtenerActivos() {
        return ResponseEntity.ok(servicioService.obtenerActivos());
    }

    @PostMapping
    public ResponseEntity<ResponseModelDTO> crear(
        @Valid @RequestBody ServicioDTO dto,
        BindingResult result) {

        if (result.hasErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                result.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.joining(", "))
            );
        }
        servicioService.crear(dto);
        return ResponseEntity.ok(
            new ResponseModelDTO(true, MensajesServicio.CREADO)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModelDTO> actualizar(
        @PathVariable Long id,
        @Valid @RequestBody ServicioDTO dto,
        BindingResult result) {

        if (result.hasErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                result.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.joining(", "))
            );
        }
        servicioService.actualizar(id, dto);
        return ResponseEntity.ok(
            new ResponseModelDTO(true, MensajesServicio.ACTUALIZADO)
        );
    }

    @PutMapping("/{id}/cambiar-estado")
    public ResponseEntity<ResponseModelDTO> cambiarEstado(
        @PathVariable Long id,
        @RequestParam("activo") int activo) {

        servicioService.cambiarEstado(id, activo);
        return ResponseEntity.ok(
            new ResponseModelDTO(true, MensajesServicio.ACTIVADO)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioService.obtenerPorId(id));
    }

    //---------MÉTODOS DELETE---------//
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModelDTO> eliminar(@PathVariable Long id) {
        servicioService.eliminar(id);
        return ResponseEntity.ok(new ResponseModelDTO(true, MensajesServicio.ELIMINADO));
    }
}
