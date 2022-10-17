package ar.edu.undec.level.storage.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recetaId;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "receta_id")
    private List<ItemReceta> listaItemsReceta;

    @OneToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Long getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(Long recetaId) {
        this.recetaId = recetaId;
    }

    public List<ItemReceta> getListaItemsReceta() {
        return listaItemsReceta;
    }

    public void setListaItemsReceta(List<ItemReceta> listaItemsReceta) {
        this.listaItemsReceta = listaItemsReceta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
