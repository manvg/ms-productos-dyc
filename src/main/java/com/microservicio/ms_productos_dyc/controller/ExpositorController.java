package com.microservicio.ms_productos_dyc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.ms_productos_dyc.model.dto.ProductoDetalleDTO;
import com.microservicio.ms_productos_dyc.model.dto.ProductoSumarioDTO;
import com.microservicio.ms_productos_dyc.service.ProductoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/expositor")
public class ExpositorController {
    
    @Autowired
    private ProductoService productoService;

    @GetMapping("/sumario")
    public ResponseEntity<List<ProductoSumarioDTO>> listarSumario() {
        return ResponseEntity.ok(productoService.listarSumario());
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<ProductoDetalleDTO> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerDetalle(id));
    }
    
    

}
