package ar.edu.undec.level.storage.entity;

import javax.persistence.*;

@Entity
public class MateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Float stock;

    private Integer cantidadMinima;

    private String descripcion;

    private Boolean alertaCantidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getStock() {
        return stock;
    }

    public void setStock(Float stock) {
        this.stock = stock;
    }

    public Integer getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(Integer cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getAlertaCantidad() {
        return alertaCantidad;
    }

    public void setAlertaCantidad(Boolean alertaCantidad) {
        this.alertaCantidad = alertaCantidad;
    }

}
