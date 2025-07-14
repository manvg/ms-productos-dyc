package com.microservicio.ms_productos_dyc.model.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDTO {

    private Long idServicio;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacía")
    private String descripcion;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0") 
    private BigDecimal precio;

    private String urlImagen;

    @NotNull
    @Min(value = 0, message = "Activo debe ser 0 o 1")
    @Max(value = 1, message = "Activo debe ser 0 o 1")
    private Integer activo;
    
}
