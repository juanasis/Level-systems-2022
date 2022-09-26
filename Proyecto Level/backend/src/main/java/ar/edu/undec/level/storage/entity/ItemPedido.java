package ar.edu.undec.level.storage.entity;

import ar.edu.undec.level.controller.dto.ItemPedidoDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido", schema = "levelbd")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "precio")
    private BigDecimal precio;

    @Basic
    @Column(name = "cantidad")
    private Integer cantidad;


//    @ManyToOne
//    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
//    private Pedido pedido;


    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Producto producto;

    public ItemPedido(ItemPedidoDto itemPedidoDto) {
        this.cantidad = itemPedidoDto.getCantidad();
        this.precio = itemPedidoDto.getPrecio();
    }

    public ItemPedido() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
//
//    public Pedido getPedido() {
//        return pedido;
//    }
//    public void setPedido(Pedido pedido) {
//        this.pedido = pedido;
//    }
//



    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }


}
