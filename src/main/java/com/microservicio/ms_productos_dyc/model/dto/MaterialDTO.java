package com.microservicio.ms_productos_dyc.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDTO {
    private Long idMaterial;

    @NotBlank(message = "El nombre del material no puede estar vacío")
    private String nombre;

    @Min(value = 0, message = "El campo 'activo' debe ser 0 o 1")
    @Max(value = 1, message = "El campo 'activo' debe ser 0 o 1")
    private Integer activo;
}
