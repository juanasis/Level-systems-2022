package ar.edu.undec.level.storage.entity;

import ar.edu.undec.level.controller.dto.ItemProductoCocinaDto;
import ar.edu.undec.level.security.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pedido implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne //muchos pedidos para un mozo
    @JoinColumn(name = "idmozo", referencedColumnName = "id", nullable = false)
    private Usuario mozo;
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne //muchos pedidos para una mesa
    @JoinColumn(name = "idmesa", referencedColumnName = "id", nullable = false)
    private Mesa mesa;
    @Column(name = "estado")
    private EstadoPedido estado;
    @Column(name = "fecha")
    private Date fecha;
    @OneToMany(mappedBy = "pedido")
    private Collection<ItemPedido> itemsList;
    private static final long serialVersionUID = 1L;




    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdMozo(Integer idMozo) { this.mozo.setId(idMozo); }
    public void setIdMesa(Integer nromesa) { this.mesa.setId(nromesa);    }
    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setitemsList(Collection<ItemPedido> itemsList) {this.itemsList = itemsList;    }
    public void setMozo(Usuario mozo) {this.mozo = mozo;}
    public void setMesa(Mesa mesa) {this.mesa = mesa;}
    public Integer getId(){
        return id;
    }

    public Mesa getNroMesa() {        return mesa;   }
    public EstadoPedido getEstado() {
        return estado;
    }
    public Date getFecha() {
        return fecha;
    }
    public Collection<ItemPedido> getItemsList() {
        return itemsList;
    }


    public Usuario getMozo() { return this.mozo;    }
}
