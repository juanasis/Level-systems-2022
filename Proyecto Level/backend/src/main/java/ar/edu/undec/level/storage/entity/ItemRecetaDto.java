package ar.edu.undec.level.storage.entity;

public class ItemRecetaDto {
    private Long materiaPrimaId;
    private String nombreMateriaPrima;
    private String descripcionMateriaPrima;
    private Float cantidad;

    public Long getMateriaPrimaId() {
        return materiaPrimaId;
    }

    public void setMateriaPrimaId(Long materiaPrimaId) {
        this.materiaPrimaId = materiaPrimaId;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreMateriaPrima() {
        return nombreMateriaPrima;
    }

    public void setNombreMateriaPrima(String nombreMateriaPrima) {
        this.nombreMateriaPrima = nombreMateriaPrima;
    }

    public String getDescripcionMateriaPrima() {
        return descripcionMateriaPrima;
    }

    public void setDescripcionMateriaPrima(String descripcionMateriaPrima) {
        this.descripcionMateriaPrima = descripcionMateriaPrima;
    }
}
