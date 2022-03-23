package ar.edu.undec.level.storage.entity;

import ar.edu.undec.level.controller.dto.ItemPedidoDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido", schema = "levelbd")
public class ItemPedido {
    private Integer id;
    private BigDecimal precio;
    private Integer cantidad;
    private Pedido pedido;
    private Producto producto;

    public ItemPedido(ItemPedidoDto itemPedidoDto) {
        this.cantidad = itemPedidoDto.getCantidad();
        this.precio = itemPedidoDto.getPrecio();
    }

    public ItemPedido() {

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
    @Basic
    @Column(name = "precio")
    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    @Basic
    @Column(name = "cantidad")
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false)
    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id", nullable = false)
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }


}
