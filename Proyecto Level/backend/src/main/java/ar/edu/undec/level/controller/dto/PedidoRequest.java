package ar.edu.undec.level.controller.dto;
import ar.edu.undec.level.storage.entity.TipoPago;

import java.util.List;

public class PedidoRequest {
    private String nombreUsuarioMozo;
    private Integer idmesa;
    private List<ItemPedidoDto> items;
    private TipoPago tipoPago;

    public PedidoRequest(String nombreUsuarioMozo, Integer idmesa, List<ItemPedidoDto> items, TipoPago tipoPago) {
        this.nombreUsuarioMozo = nombreUsuarioMozo;
        this.idmesa = idmesa;
        this.items = items;
        this.tipoPago = tipoPago;
    }

    public String getNombreUsuarioMozo() {
        return nombreUsuarioMozo;
    }

    public Integer getIdmesa() {
        return idmesa;
    }

    public void setIdmesa(Integer idmesa) {
        this.idmesa = idmesa;
    }

    public void setItems(List<ItemPedidoDto> items) {
        this.items = items;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public void setNombreUsuarioMozo(String nombreUsuarioMozo) {
        this.nombreUsuarioMozo = nombreUsuarioMozo;
    }

    public Integer getIdMesa() {
        return idmesa;
    }

    public List<ItemPedidoDto> getItems() {
        return items;
    }
}
