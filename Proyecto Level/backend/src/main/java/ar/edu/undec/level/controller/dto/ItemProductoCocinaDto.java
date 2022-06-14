package ar.edu.undec.level.controller.dto;

import ar.edu.undec.level.storage.entity.Categoria;

import java.math.BigDecimal;

public class ItemProductoCocinaDto {
    private String nombre;
    private Integer cantidad;
    private Categoria categoria;

    public ItemProductoCocinaDto(){}
    public ItemProductoCocinaDto(String nombre,  Integer cantidad,Categoria categoria) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
