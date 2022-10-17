package ar.edu.undec.level.service;

import ar.edu.undec.level.mapper.IMapper;
import ar.edu.undec.level.mapper.MapperImpl;
import ar.edu.undec.level.storage.entity.Receta;
import ar.edu.undec.level.storage.entity.RecetaDtoOut;
import ar.edu.undec.level.storage.repository.MateriaPrimaRepository;
import ar.edu.undec.level.storage.repository.ProductosRepository;
import ar.edu.undec.level.storage.repository.RecetaRepository;
import ar.edu.undec.level.utils.EntityMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecetaServiceTest {

    private RecetaRepository recetaRepository;

    private ProductosRepository productosRepository;

    private IMapper mapper;

    private RecetaService recetaService;


    @BeforeEach
    void setUp() {
        recetaRepository = mock(RecetaRepository.class);
        productosRepository = mock(ProductosRepository.class);
        mapper = mock(MapperImpl.class);
        recetaService = new RecetaService(recetaRepository, productosRepository, mapper);
    }

    @Test
    public void obtenerRecetaPorProductIdFullTest() {
        when(recetaRepository.obtenerRecetaPorProductId(1)).thenReturn(Optional.of(EntityMock.recetaMock()));
        when(mapper.mapOutRecetaDto(any(Receta.class))).thenReturn(EntityMock.recetaDtoOutMock());
        RecetaDtoOut recetaDtoOut = recetaService.obtenerRecetaPorProductId(1);

        assertNotNull(recetaDtoOut);
        assertNotNull(recetaDtoOut.getNombreRecetaProducto());
        assertNotNull(recetaDtoOut.getListaRecetaItems());
        assertNotNull(recetaDtoOut.getListaRecetaItems().get(0));
        assertNotNull(recetaDtoOut.getListaRecetaItems().get(0).getNombreMateriaPrima());
        assertNotNull(recetaDtoOut.getListaRecetaItems().get(0).getDescripcionMateriaPrima());
        assertNotNull(recetaDtoOut.getListaRecetaItems().get(0).getCantidad());

        assertEquals(recetaDtoOut.getNombreRecetaProducto(), EntityMock.recetaMock().getProducto().getNombre());
        assertEquals(recetaDtoOut.getListaRecetaItems().size(), EntityMock.recetaMock().getListaItemsReceta().size());
        assertEquals(recetaDtoOut.getListaRecetaItems().get(0).getNombreMateriaPrima(), EntityMock.recetaMock().getListaItemsReceta().get(0).getMateriaPrima().getNombre());
        assertEquals(recetaDtoOut.getListaRecetaItems().get(0).getDescripcionMateriaPrima(), EntityMock.recetaMock().getListaItemsReceta().get(0).getMateriaPrima().getDescripcion());
        assertEquals(recetaDtoOut.getListaRecetaItems().get(0).getCantidad(), EntityMock.recetaMock().getListaItemsReceta().get(0).getCantidad());
    }

    @Test
    public void obtenerRecetaPorProductIdEmptyTest() {
        Integer productoId = 1;
        when(recetaRepository.obtenerRecetaPorProductId(1)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> recetaService.obtenerRecetaPorProductId(productoId));

        verify(recetaRepository, times(1)).obtenerRecetaPorProductId(anyInt());
        verify(mapper, times(0)).mapOutRecetaDto(any(Receta.class));
    }

    @Test
    public void crearRecetaFullTest() {
        when(productosRepository.findById(anyInt())).thenReturn(Optional.of(EntityMock.productoMock()));
        when(recetaRepository.obtenerRecetaPorProductId(anyInt())).thenReturn(Optional.empty());
        recetaService.crearReceta(EntityMock.recetaDtoInMock());

        verify(productosRepository, times(1)).findById(anyInt());
        verify(recetaRepository, times(1)).obtenerRecetaPorProductId(anyInt());
    }

    @Test
    public void numeroDeInvocaciones() {
        when(recetaRepository.obtenerRecetaPorProductId(1)).thenReturn(Optional.of(EntityMock.recetaMock()));
        when(mapper.mapOutRecetaDto(any(Receta.class))).thenReturn(EntityMock.recetaDtoOutMock());
        recetaService.obtenerRecetaPorProductId(1);

        verify(recetaRepository).obtenerRecetaPorProductId(1);
        verify(mapper, times(1)).mapOutRecetaDto(any(Receta.class));
    }


}