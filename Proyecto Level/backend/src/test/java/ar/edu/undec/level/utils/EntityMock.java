package ar.edu.undec.level.utils;

import ar.edu.undec.level.storage.entity.*;

import java.util.ArrayList;
import java.util.List;

public final class EntityMock {

    private static final EntityMock INSTANCE = new EntityMock();

    public static EntityMock getInstance() {
        return INSTANCE;
    }

    public static Receta recetaMock() {
        Receta receta = new Receta();
        receta.setRecetaId(1L);
        Producto producto = new Producto();
        List<ItemReceta> items = new ArrayList<>();
        ItemReceta itemReceta = new ItemReceta();
        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setId(1L);
        materiaPrima.setNombre("Papa");
        materiaPrima.setDescripcion("KG");
        itemReceta.setCantidad(200.0);
        itemReceta.setMateriaPrima(materiaPrima);
        items.add(itemReceta);
        receta.setListaItemsReceta(items);
        producto.setId(1);
        producto.setNombre("LOMO SALTADO");
        receta.setProducto(producto);
        return receta;
    }

    public static RecetaDtoIn recetaDtoInMock() {
        RecetaDtoIn recetaDtoIn = new RecetaDtoIn();
        recetaDtoIn.setProductoId(1);
        List<ItemRecetaDto> items = new ArrayList<>();
        ItemRecetaDto itemRecetaDto = new ItemRecetaDto();
        itemRecetaDto.setCantidad(200.0);
        itemRecetaDto.setMateriaPrimaId(1L);
        items.add(itemRecetaDto);
        recetaDtoIn.setListaItemsReceta(items);
        return recetaDtoIn;
    }

    public static RecetaDtoOut recetaDtoOutMock() {
        RecetaDtoOut recetaDtoOut = new RecetaDtoOut();
        recetaDtoOut.setNombreRecetaProducto("LOMO SALTADO");
        List<ItemRecetaDto> items = new ArrayList<>();
        ItemRecetaDto itemRecetaDto = new ItemRecetaDto();
        itemRecetaDto.setCantidad(200.0);
        itemRecetaDto.setDescripcionMateriaPrima("KG");
        itemRecetaDto.setNombreMateriaPrima("Papa");
        items.add(itemRecetaDto);
        recetaDtoOut.setListaRecetaItems(items);
        return recetaDtoOut;
    }

    public static Producto productoMock() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("LOMO SALTADO");
        return producto;
    }
}
