package ar.edu.undec.level.mapper;

import ar.edu.undec.level.storage.entity.*;
import ar.edu.undec.level.utils.EntityMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperImplTest {

    private IMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new MapperImpl();
    }

    @Test
    public void mapInRecetaFullTest() {

        Receta receta = mapper.mapInReceta(EntityMock.recetaDtoInMock());

        assertNotNull(receta);
        assertNotNull(receta.getProducto());
        assertNotNull(receta.getListaItemsReceta());

        assertNotNull(receta.getProducto().getId());
        assertNotNull(receta.getListaItemsReceta().get(0));
        assertNotNull(receta.getListaItemsReceta().get(0).getMateriaPrima().getId());
        assertNotNull(receta.getListaItemsReceta().get(0).getCantidad());
        assertNull(receta.getListaItemsReceta().get(0).getItemRecetaId());
        assertNull(receta.getRecetaId());

        assertEquals(receta.getProducto().getId(), 1);
        assertEquals(receta.getListaItemsReceta().get(0).getCantidad(), 200);
        assertEquals(receta.getListaItemsReceta().get(0).getMateriaPrima().getId(), 1L);
    }

    @Test
    public void mapInRecetaEmpty() {
        Receta receta = mapper.mapInReceta(null);

        assertNull(receta);
    }

    @Test
    public void mapOutRecetaFullTest() {

        RecetaDtoOut recetaDtoOut = mapper.mapOutRecetaDto(EntityMock.recetaMock());

        assertNotNull(recetaDtoOut);
        assertNotNull(recetaDtoOut.getNombreRecetaProducto());
        assertNotNull(recetaDtoOut.getListaRecetaItems());
        assertNotNull(recetaDtoOut.getListaRecetaItems().get(0));
        assertNotNull(recetaDtoOut.getListaRecetaItems().get(0).getCantidad());
        assertNotNull(recetaDtoOut.getListaRecetaItems().get(0).getDescripcionMateriaPrima());
        assertNotNull(recetaDtoOut.getListaRecetaItems().get(0).getNombreMateriaPrima());
        assertNotNull(recetaDtoOut.getListaRecetaItems().get(0).getMateriaPrimaId());

        assertEquals(recetaDtoOut.getNombreRecetaProducto(), "LOMO SALTADO");
        assertEquals(recetaDtoOut.getListaRecetaItems().get(0).getCantidad(), 200);
        assertEquals(recetaDtoOut.getListaRecetaItems().get(0).getDescripcionMateriaPrima(), "KG");
        assertEquals(recetaDtoOut.getListaRecetaItems().get(0).getNombreMateriaPrima(), "Papa");

    }

    @Test
    public void mapOutRecetaDtoEmpty() {
        RecetaDtoOut recetaDtoOut = mapper.mapOutRecetaDto(null);
        assertNull(recetaDtoOut);
    }

}