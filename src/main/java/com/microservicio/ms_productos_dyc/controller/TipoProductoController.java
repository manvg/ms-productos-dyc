package com.microservicio.ms_productos_dyc.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.ms_productos_dyc.model.dto.ResponseModelDTO;
import com.microservicio.ms_productos_dyc.model.dto.TipoProductoDTO;
import com.microservicio.ms_productos_dyc.service.TipoProductoService.TipoProductoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/productos/tipos")
public class TipoProductoController {
    
    @Autowired
    private TipoProductoService tipoProductoService;

    @GetMapping
    public ResponseEntity<List<TipoProductoDTO>> obtenerTodos() {
        return ResponseEntity.ok(tipoProductoService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<ResponseModelDTO> crear(
        @Valid @RequestBody TipoProductoDTO dto, BindingResult result) {

        if (result.hasErrors()) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            result.getFieldErrors()
                .stream()
                .map(e -> e.getField()+": "+e.getDefaultMessage())
                .collect(Collectors.joining(", "))
        );
        }
        tipoProductoService.crear(dto);
        return ResponseEntity.ok(
        new ResponseModelDTO(true, "Tipo de producto creado correctamente")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModelDTO> actualizar(
        @PathVariable Long id,
        @Valid @RequestBody TipoProductoDTO dto,
        BindingResult result) {

        if (result.hasErrors()) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            result.getFieldErrors()
                .stream()
                .map(e -> e.getField()+": "+e.getDefaultMessage())
                .collect(Collectors.joining(", "))
        );
        }
        tipoProductoService.actualizar(id, dto);
        return ResponseEntity.ok(
        new ResponseModelDTO(true, "Tipo de producto actualizado correctamente")
        );
    }

    @PutMapping("/{id}/cambiar-estado")
    public ResponseEntity<ResponseModelDTO> cambiarEstado(@PathVariable Long id, @RequestParam("activo") int activo) {
        tipoProductoService.cambiarEstado(id, activo);
        return ResponseEntity.ok(new ResponseModelDTO(true, "Estado actualizado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipoProductoService.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModelDTO> eliminar(@PathVariable Long id) {
        tipoProductoService.eliminar(id);
        return ResponseEntity.ok(new ResponseModelDTO(true, "Tipo producto eliminado correctamente"));
    }
}
