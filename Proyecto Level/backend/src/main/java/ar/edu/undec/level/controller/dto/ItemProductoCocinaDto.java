package ar.edu.undec.level.controller.dto;

import java.math.BigDecimal;

public class ItemProductoCocinaDto {
    private String nombre;
    private Integer cantidad;
    private String categoria;

    public ItemProductoCocinaDto(){}
    public ItemProductoCocinaDto(String nombre,  Integer cantidad,String categoria) {
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
