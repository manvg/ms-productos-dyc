package com.microservicio.ms_productos_dyc.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoSumarioDTO {
    private Long idProducto;
    private String nombre;
    private BigDecimal precio;
    private String urlImagen;
}
