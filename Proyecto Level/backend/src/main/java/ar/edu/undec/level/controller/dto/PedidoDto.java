package ar.edu.undec.level.controller.dto;

import ar.edu.undec.level.storage.entity.EstadoPedido;
import ar.edu.undec.level.storage.entity.ItemPedido;
import ar.edu.undec.level.storage.entity.Pedido;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PedidoDto {
    private Integer id;
    private EstadoPedido estado;
    private LocalDateTime fecha;
    private List<ItemProductoDto> itemProductoDtoList = new ArrayList<>();
    private Integer idMesa;
    private String nombreMozo;


    public PedidoDto() {
    }

    public PedidoDto(Pedido pedido) {
        this.id = pedido.getId();
        this.estado = pedido.getEstado();
        this.fecha = pedido.getFecha();
//        this.idMesa = pedido.getNroMesa().getId();
        this.itemProductoDtoList = getProdutos(pedido.getItemsList());
        this.nombreMozo = pedido.getMozo().getNombre();
    }

    private List<ItemProductoDto> getProdutos(Collection<ItemPedido> itemsList) {
        List<ItemProductoDto> result = new ArrayList<>();
        for (ItemPedido item: itemsList     ) {
            ItemProductoDto itemDto = new ItemProductoDto();
            itemDto.setNombre(item.getProducto().getNombre());
            itemDto.setCantidad(item.getCantidad());
            itemDto.setPrecio(item.getPrecio());
            result.add(itemDto);
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<ItemProductoDto> getItemProductoDtoList() {
        return itemProductoDtoList;
    }

    public void setItemProductoDtoList(List<ItemProductoDto> itemProductoDtoList) {
        this.itemProductoDtoList = itemProductoDtoList;
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
