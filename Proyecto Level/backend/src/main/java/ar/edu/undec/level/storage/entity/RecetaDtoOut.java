package ar.edu.undec.level.storage.entity;

import java.util.List;

public class RecetaDtoOut {
    private String nombreRecetaProducto;
    private List<ItemRecetaDto> listaRecetaItems;

    public String getNombreRecetaProducto() {
        return nombreRecetaProducto;
    }

    public void setNombreRecetaProducto(String nombreRecetaProducto) {
        this.nombreRecetaProducto = nombreRecetaProducto;
    }

    public List<ItemRecetaDto> getListaRecetaItems() {
        return listaRecetaItems;
    }

    public void setListaRecetaItems(List<ItemRecetaDto> listaRecetaItems) {
        this.listaRecetaItems = listaRecetaItems;
    }
}
