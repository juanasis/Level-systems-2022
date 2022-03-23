package ar.edu.undec.level.controller.dto;

import ar.edu.undec.level.storage.entity.EstadoPedido;
import ar.edu.undec.level.storage.entity.ItemPedido;
import ar.edu.undec.level.storage.entity.Pedido;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PedidoDtoCocina {
    private Integer id;
    private EstadoPedido estado;
    private Date fecha;
    private List<ItemProductoCocinaDto> itemProductoCocinaDto = new ArrayList<>();
    private Integer idMesa;
    private String nombreMozo;


    public PedidoDtoCocina() {
    }

    public PedidoDtoCocina(Pedido pedido) {
        if(!getProdutos(pedido.getItemsList()).isEmpty()){
            this.id = pedido.getId();
            this.estado = pedido.getEstado();
            this.fecha = pedido.getFecha();
            this.idMesa = pedido.getNroMesa().getId();
            this.itemProductoCocinaDto = getProdutos(pedido.getItemsList());
            this.nombreMozo = pedido.getMozo().getNombre();
        }else{
            this.id = null;
        }

    }

    private List<ItemProductoCocinaDto> getProdutos(Collection<ItemPedido> itemsList) {
        List<ItemProductoCocinaDto> result = new ArrayList<>();
        for (ItemPedido item: itemsList     ) {
            ItemProductoCocinaDto itemDto = new ItemProductoCocinaDto();
            itemDto.setNombre(item.getProducto().getNombre());
            itemDto.setCantidad(item.getCantidad());
            itemDto.setCategoria(item.getProducto().getCategoria());
            if(item.getProducto().getCategoria().equalsIgnoreCase( "menu") || item.getProducto().getCategoria().equalsIgnoreCase( "sanwiches") ){
                result.add(itemDto);

            }

        }

        return result;
    }

    public Integer getId() {
        return id;
    }



    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<ItemProductoCocinaDto> getItemProductoCocinaDto() {
        return itemProductoCocinaDto;
    }

    public void setItemProductoCocinaDto(List<ItemProductoCocinaDto> itemProductoCocinaDto) {
        this.itemProductoCocinaDto = itemProductoCocinaDto;
    }

    public Integer getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    public String getNombreMozo() {
        return nombreMozo;
    }

    public void setNombreMozo(String nombreMozo) {
        this.nombreMozo = nombreMozo;
    }
}
