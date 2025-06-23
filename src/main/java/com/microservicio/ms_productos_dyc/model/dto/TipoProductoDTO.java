package com.microservicio.ms_productos_dyc.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoProductoDTO {
    private Long idTipoProducto;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Min(0)
    @Max(1)
    private Integer activo;
}