package ar.edu.undec.level.controller.dto;

import ar.edu.undec.level.storage.entity.EstadoMesa;



public class MesaRequest {
    private Integer id;
    private EstadoMesa estadoMesa;


    public MesaRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoMesa getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(EstadoMesa estadoMesa) {
        this.estadoMesa = estadoMesa;
    }
}
