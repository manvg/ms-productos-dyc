package com.microservicio.ms_productos_dyc.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoProductoDTO {
    private Long idTipoProducto;
    private String nombre;
    private Integer activo;
}