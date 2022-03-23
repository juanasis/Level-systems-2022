package ar.edu.undec.level.controller.dto;
import java.util.List;

public class PedidoRequest {
    private Integer idmozo;
    private Integer idmesa;
    private List<ItemPedidoDto> items;

    public PedidoRequest(Integer idmozo, Integer idmesa, List<ItemPedidoDto> items) {
        this.idmozo = idmozo;
        this.idmesa = idmesa;
        this.items = items;
    }
    public Integer getIdMozo() {
        return idmozo;
    }

    public Integer getIdMesa() {
        return idmesa;
    }

    public List<ItemPedidoDto> getItems() {
        return items;
    }
}
