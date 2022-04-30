package ar.edu.undec.level.storage.entity;
import ar.edu.undec.level.security.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Mesa implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "estado")
    private EstadoMesa estado;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mesa", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"nroMesa","hibernateLazyInitializer", "handler"})
    private List<Pedido> pedidos;


    private Integer mozoId;

    private static final long serialVersionUID = 1L;

    public Integer getMozoId() {
        return mozoId;
    }

    public void setMozoId(Integer mozoId) {
        this.mozoId = mozoId;
    }

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

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
