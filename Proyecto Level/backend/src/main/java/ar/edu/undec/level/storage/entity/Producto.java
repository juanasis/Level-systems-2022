package ar.edu.undec.level.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Producto implements Serializable {
   private Integer id;
   private String nombre;
   private String descripcion;
   private Integer cantidad;
   private  String categoria;
   private  String imgpath;
   private Double precio;
   private EstadoProducto estado;
   private Collection<ItemPedido> pedidosList;

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", categoria='" + categoria + '\'' +
                ", imgpath='" + imgpath + '\'' +
                ", precio=" + precio +
                ", estado=" + estado +
                '}';
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "cantidad")
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Column(name = "categoria")
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Column(name = "precio")
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Column(name = "imgpath")
    public String getImgpath() {
        return imgpath;
    }
    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    @OneToMany(mappedBy = "producto")
    public Collection<ItemPedido> getPedidosList() {
        return pedidosList;
    }
    public void setPedidosList(Collection<ItemPedido> pedidosList) {
        this.pedidosList = pedidosList;
    }
}
