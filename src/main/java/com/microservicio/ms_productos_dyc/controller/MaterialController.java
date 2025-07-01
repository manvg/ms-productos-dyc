package com.microservicio.ms_productos_dyc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.ms_productos_dyc.model.dto.MaterialDTO;
import com.microservicio.ms_productos_dyc.model.dto.ResponseModelDTO;
import com.microservicio.ms_productos_dyc.service.MaterialSerice.MaterialService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/materiales")
@Validated
public class MaterialController {
    
    @Autowired
    private MaterialService materialService;

    @GetMapping
    public ResponseEntity<List<MaterialDTO>> obtenerTodos() {
        return ResponseEntity.ok(materialService.obtenerTodos());
    }

    @GetMapping("/sans")
    public ResponseEntity<ResponseModelDTO> sans() {
        return ResponseEntity.ok(new ResponseModelDTO(true, "Te pica la patita"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ResponseModelDTO> crear(@Valid @RequestBody MaterialDTO dto) {
        materialService.crear(dto);
        return ResponseEntity.ok(new ResponseModelDTO(true, "Material creado correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModelDTO> actualizar(@PathVariable Long id, @Valid @RequestBody MaterialDTO dto) {
        materialService.actualizar(id, dto);
        return ResponseEntity.ok(new ResponseModelDTO(true, "Material actualizado corectamente"));
    }

    @PutMapping("/{id}/cambiar-estado")
    public ResponseEntity<ResponseModelDTO> cambiarEstado(@PathVariable Long id, @RequestParam("activo") int activo) {
        if (activo != 0 && activo != 1) {
            return ResponseEntity.badRequest()
                .body(new ResponseModelDTO(false, "El parámetro 'activo' debe ser 0 o 1"));
        }
        materialService.cambiarEstado(id, activo);
        String mensaje = activo ==1 ? "Material activado" : "Material desactivado";
        return ResponseEntity.ok(new ResponseModelDTO(true, mensaje));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModelDTO> eliminar(@PathVariable Long id) {
        materialService.eliminar(id);
        return ResponseEntity.ok(new ResponseModelDTO(true, "Material eliminado correctamente"));
    }
    
}
