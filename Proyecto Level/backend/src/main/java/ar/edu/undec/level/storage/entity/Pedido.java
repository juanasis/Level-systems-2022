package ar.edu.undec.level.storage.entity;

import ar.edu.undec.level.controller.dto.ItemProductoCocinaDto;
import ar.edu.undec.level.security.entity.Usuario;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @JoinColumn(name = "idmozo", nullable = true)
    private Usuario mozo;

//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY) //muchos pedidos para una mesa
    @JsonIgnoreProperties({"pedidos","hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "idmesa", referencedColumnName = "id")
    private Mesa mesa;

    @Column(name = "estado")
    private EstadoPedido estado;

    private TipoPago tipoPago;

    private String emailUsuario;

    @Column(name = "fecha")
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime fecha;

    @Column(name = "fecha_query")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaQuery;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"pedido","hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "pedido_id")
    private Collection<ItemPedido> itemsList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caja")
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Caja caja;


    private static final long serialVersionUID = 1L;

    @PrePersist
    public void prePersist(){
        this.fecha = LocalDateTime.now();
        this.fechaQuery = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getMozo() {
        return mozo;
    }

    public void setMozo(Usuario mozo) {
        this.mozo = mozo;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaQuery() {
        return fechaQuery;
    }

    public void setFechaQuery(LocalDate fechaQuery) {
        this.fechaQuery = fechaQuery;
    }

    public Collection<ItemPedido> getItemsList() {
        return itemsList;
    }

    public void setItemsList(Collection<ItemPedido> itemsList) {
        this.itemsList = itemsList;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
