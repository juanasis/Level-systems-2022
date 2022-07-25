package ar.edu.undec.level.storage.entity;

import ar.edu.undec.level.security.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class CajaDtoOut {

    private Long idCaja;

    private EstadoCaja estado;

    private Double monto_inicial;

    private Double monto_final;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime fecha_apertura;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime fecha_cierre;

    private Usuario cajero;

    private List<Pedido> pedidos;

    public Long getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Long idCaja) {
        this.idCaja = idCaja;
    }

    public EstadoCaja getEstado() {
        return estado;
    }

    public void setEstado(EstadoCaja estado) {
        this.estado = estado;
    }

    public Double getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(Double monto_inicial) {
        this.monto_inicial = monto_inicial;
    }

    public Double getMonto_final() {
        return monto_final;
    }

    public void setMonto_final(Double monto_final) {
        this.monto_final = monto_final;
    }

    public LocalDateTime getFecha_apertura() {
        return fecha_apertura;
    }

    public void setFecha_apertura(LocalDateTime fecha_apertura) {
        this.fecha_apertura = fecha_apertura;
    }

    public LocalDateTime getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(LocalDateTime fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public Usuario getCajero() {
        return cajero;
    }

    public void setCajero(Usuario cajero) {
        this.cajero = cajero;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
