package ar.edu.undec.level.storage.entity;

import ar.edu.undec.level.security.entity.Usuario;


public class CajaDtoIn {
    private Usuario cajero;
    private Double monto_inicial;

    public Usuario getCajero() {
        return cajero;
    }

    public void setCajero(Usuario cajero) {
        this.cajero = cajero;
    }

    public Double getMonto_inicial() {
        return monto_inicial;
    }

    public void setMonto_inicial(Double monto_inicial) {
        this.monto_inicial = monto_inicial;
    }
}
