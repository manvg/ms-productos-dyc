package com.microservicio.ms_productos_dyc.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//new 30/06/25

@Entity
@Table(name="material")
public class Material {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Long idMaterial;
    
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Integer activo = 1;

    //Getter
    public Long getIdMaterial() {
        return idMaterial;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getActivo() {
        return activo;
    }

    //Setter
    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

}
