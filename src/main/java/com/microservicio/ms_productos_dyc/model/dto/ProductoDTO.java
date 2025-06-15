package com.microservicio.ms_productos_dyc.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private String material;
    private String medidas;
    private BigDecimal precio;
    private String urlImagen;
    private Integer activo;
    private Long idTipoProducto;
}