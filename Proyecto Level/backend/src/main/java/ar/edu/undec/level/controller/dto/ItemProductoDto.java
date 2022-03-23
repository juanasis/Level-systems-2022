package ar.edu.undec.level.controller.dto;

import java.math.BigDecimal;

public class ItemProductoDto {
    private String nombre;
    private BigDecimal precio;
    private Integer cantidad;

    public ItemProductoDto(){}
    public ItemProductoDto(String nombre, BigDecimal precio, Integer cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
