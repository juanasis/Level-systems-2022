package ar.edu.undec.level.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class ItemReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemRecetaId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_prima_id", nullable = false)
    private MateriaPrima materiaPrima;

    private Double cantidad;

    public Long getItemRecetaId() {
        return itemRecetaId;
    }

    public void setItemRecetaId(Long itemRecetaId) {
        this.itemRecetaId = itemRecetaId;
    }

    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
}
