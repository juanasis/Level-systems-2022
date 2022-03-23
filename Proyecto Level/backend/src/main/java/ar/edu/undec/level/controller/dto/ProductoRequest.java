package ar.edu.undec.level.controller.dto;

import ar.edu.undec.level.storage.entity.EstadoProducto;
import ar.edu.undec.level.storage.entity.Producto;

public class ProductoRequest {
    public String nombre;
    public String descripcion;
    public int cantidad;
    public String categoria;
    public double precio;
    public String imgpath;
    public EstadoProducto estadoProducto;

    public ProductoRequest() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public ProductoRequest getProductoDTO(Producto producto){
        ProductoRequest productoRequest = new ProductoRequest();
        productoRequest.setCantidad(producto.getCantidad());
        productoRequest.setCategoria(producto.getCategoria());
        productoRequest.setDescripcion(producto.getDescripcion());
        productoRequest.setImgpath(producto.getImgpath());
        productoRequest.setPrecio(producto.getPrecio());
        productoRequest.setNombre(producto.getNombre());

        return productoRequest;
    }


    public ProductoRequest(String nombre, String descripcion, int cantidad, String categoria, double precio, String imgpath) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.precio = precio;
        this.imgpath = imgpath;
    }

    public EstadoProducto getEstado() {
        return this.estadoProducto;
    }
}
