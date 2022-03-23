package ar.edu.undec.level.controller.dto;

import java.math.BigDecimal;

public class ItemPedidoDto {
    private Integer producto_id;
    private Integer cantidad;
    private BigDecimal precio;

    public ItemPedidoDto() {
    }

    public Integer getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(Integer producto_id) {
        this.producto_id = producto_id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
