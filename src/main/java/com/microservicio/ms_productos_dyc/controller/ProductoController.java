package com.microservicio.ms_productos_dyc.controller;

import com.microservicio.ms_productos_dyc.model.dto.ProductoDTO;
import com.microservicio.ms_productos_dyc.model.dto.ResponseModelDTO;
import com.microservicio.ms_productos_dyc.service.ProductoService;
import com.microservicio.ms_productos_dyc.utilities.MensajesProducto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    //---------MÉTODOS GET---------//
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ProductoDTO>> obtenerActivos() {
        return ResponseEntity.ok(productoService.obtenerActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    //---------MÉTODOS POST---------//
    @PostMapping
    public ResponseEntity<ResponseModelDTO> crear(@RequestBody ProductoDTO dto) {
        productoService.crear(dto);
        return ResponseEntity.ok(new ResponseModelDTO(true, MensajesProducto.CREADO));
    }

    //---------MÉTODOS PUT---------//
    @PutMapping("/{id}")
    public ResponseEntity<ResponseModelDTO> actualizar(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        productoService.actualizar(id, dto);
        return ResponseEntity.ok(new ResponseModelDTO(true, MensajesProducto.ACTUALIZADO));
    }

    @PutMapping("/{id}/cambiar-estado")
    public ResponseEntity<ResponseModelDTO> cambiarEstado(@PathVariable Long id, @RequestParam("activo") int activo) {
        if (activo != 0 && activo != 1) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(false, MensajesProducto.ESTADO_INVALIDO));
        }

        productoService.cambiarEstado(id, activo);
        String mensaje = (activo == 1) ? MensajesProducto.ACTIVADO : MensajesProducto.DESACTIVADO;
        return ResponseEntity.ok(new ResponseModelDTO(true, mensaje));
    }

    //---------MÉTODOS DELETE---------//
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModelDTO> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.ok(new ResponseModelDTO(true, MensajesProducto.ELIMINADO));
    }
}