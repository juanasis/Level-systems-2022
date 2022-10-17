package ar.edu.undec.level.storage.entity;

import java.util.List;

public class RecetaDtoIn {
    private List<ItemRecetaDto> listaItemsReceta;
    private Integer productoId;

    public List<ItemRecetaDto> getListaItemsReceta() {
        return listaItemsReceta;
    }

    public void setListaItemsReceta(List<ItemRecetaDto> listaItemsReceta) {
        this.listaItemsReceta = listaItemsReceta;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
}
