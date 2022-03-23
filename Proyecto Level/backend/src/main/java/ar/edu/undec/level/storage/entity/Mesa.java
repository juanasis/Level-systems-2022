package ar.edu.undec.level.storage.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Mesa implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "estado")
    private EstadoMesa estado;
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    private Collection<Pedido> pedidos;
    private static final long serialVersionUID = 1L;

    public Integer getId(){
        return id;
    }
    public void setId(Integer id) {        this.id = id;    }

    public EstadoMesa getEstado() {
        return estado;
    }
    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



    public Collection<Pedido> getPedidosList() {
        return pedidos;
    }
    public void setPedidosList(Collection<Pedido> pedidosList) {
        this.pedidos = pedidosList;
    }
}
